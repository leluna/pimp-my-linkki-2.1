package org.linkki.devconf2022.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import org.linkki.core.binding.descriptor.aspect.Aspect;
import org.linkki.core.binding.descriptor.aspect.LinkkiAspectDefinition;
import org.linkki.core.binding.descriptor.aspect.annotation.AspectDefinitionCreator;
import org.linkki.core.binding.descriptor.aspect.annotation.LinkkiAspect;
import org.linkki.core.binding.descriptor.aspect.base.ModelToUiAspectDefinition;
import org.linkki.core.binding.descriptor.property.annotation.BoundPropertyCreator;
import org.linkki.core.binding.descriptor.property.annotation.LinkkiBoundProperty;
import org.linkki.core.binding.uicreation.LinkkiComponent;
import org.linkki.core.binding.uicreation.LinkkiComponentDefinition;
import org.linkki.core.binding.wrapper.ComponentWrapper;
import org.linkki.core.ui.wrapper.NoLabelComponentWrapper;
import org.linkki.core.ui.wrapper.VaadinComponentWrapper;
import org.linkki.core.uicreation.ComponentAnnotationReader;
import org.linkki.core.uicreation.ComponentDefinitionCreator;
import org.linkki.core.uicreation.UiCreator;
import org.linkki.core.uicreation.layout.LayoutDefinitionCreator;
import org.linkki.core.uicreation.layout.LinkkiLayout;
import org.linkki.core.uicreation.layout.LinkkiLayoutDefinition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.TYPE_USE})
@LinkkiLayout(UISplitLayout.SplitLayoutLayoutDefinitionCreator.class)
@LinkkiComponent(UISplitLayout.SplitLayoutComponentDefinitionCreator.class)
@LinkkiAspect(UISplitLayout.SplitterPositionAspectDefinitionCreator.class)
@LinkkiBoundProperty(BoundPropertyCreator.EmptyPropertyCreator.class)
public @interface UISplitLayout {

    double splitterPosition() default -1;

    SplitLayout.Orientation orientation() default SplitLayout.Orientation.HORIZONTAL;

    @Target(ElementType.METHOD)
    @Retention(RUNTIME)
    @interface Primary {
        // marker interface
    }

    @Target(ElementType.METHOD)
    @Retention(RUNTIME)
    @interface Secondary {
        // marker interface
    }

    class SplitLayoutComponentDefinitionCreator implements ComponentDefinitionCreator<UISplitLayout> {

        @Override
        public LinkkiComponentDefinition create(UISplitLayout uiSplitLayout, AnnotatedElement annotatedElement) {
            return o -> {
                var splitLayout = new SplitLayout(uiSplitLayout.orientation());
                splitLayout.setSizeFull();
                splitLayout.setSplitterPosition(uiSplitLayout.splitterPosition());
                return splitLayout;
            };
        }
    }

    class SplitLayoutLayoutDefinitionCreator implements LayoutDefinitionCreator<UISplitLayout> {

        @Override
        public LinkkiLayoutDefinition create(UISplitLayout uiSplitLayout, AnnotatedElement annotatedElement) {
            return (parent, pmo, bindingContext) -> {
                SplitLayout splitLayout = (SplitLayout)parent;
                Function<Component, VaadinComponentWrapper> componentWrapperCreator = NoLabelComponentWrapper::new;
                var componentDefinitionMethods = ComponentAnnotationReader.getComponentDefinitionMethods(pmo.getClass()).collect(Collectors.toList());
                componentDefinitionMethods.stream()
                        .filter(m -> m.isAnnotationPresent(Primary.class))
                        .map(m -> UiCreator.createUiElement(m, pmo, bindingContext, componentWrapperCreator))
                        .map(VaadinComponentWrapper::getComponent)
                        .forEach(splitLayout::addToPrimary);

                componentDefinitionMethods.stream()
                        .filter(m -> m.isAnnotationPresent(Secondary.class))
                        .map(m -> UiCreator.createUiElement(m, pmo, bindingContext, componentWrapperCreator))
                        .map(VaadinComponentWrapper::getComponent)
                        .forEach(splitLayout::addToSecondary);
            };
        }
    }

    class SplitterPositionAspectDefinitionCreator implements AspectDefinitionCreator<UISplitLayout> {

        @Override
        public LinkkiAspectDefinition create(UISplitLayout uiSplitLayout) {
            return new SplitterPositionAspectDefinition(uiSplitLayout.splitterPosition());
        }
    }

    class SplitterPositionAspectDefinition extends ModelToUiAspectDefinition<Double> {

        public static final String NAME = "splitterPosition";
        private final double fixedSplitterPosition;

        public SplitterPositionAspectDefinition(double fixedSplitterPosition) {
            this.fixedSplitterPosition = fixedSplitterPosition;
        }

        @Override
        public Aspect<Double> createAspect() {
            if (fixedSplitterPosition < 0) {
                return Aspect.of(NAME);
            } else {
                return Aspect.of(NAME, fixedSplitterPosition);
            }
        }

        @Override
        public Consumer<Double> createComponentValueSetter(ComponentWrapper componentWrapper) {
            SplitLayout splitLayout = (SplitLayout)componentWrapper.getComponent();
            return splitLayout::setSplitterPosition;
        }
    }
}
