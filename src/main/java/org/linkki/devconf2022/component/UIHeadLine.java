package org.linkki.devconf2022.component;

import com.vaadin.flow.component.Component;
import org.apache.commons.lang3.StringUtils;
import org.linkki.core.binding.descriptor.aspect.Aspect;
import org.linkki.core.binding.descriptor.aspect.LinkkiAspectDefinition;
import org.linkki.core.binding.descriptor.aspect.annotation.AspectDefinitionCreator;
import org.linkki.core.binding.descriptor.aspect.annotation.LinkkiAspect;
import org.linkki.core.binding.descriptor.aspect.base.ModelToUiAspectDefinition;
import org.linkki.core.binding.uicreation.LinkkiComponent;
import org.linkki.core.binding.uicreation.LinkkiComponentDefinition;
import org.linkki.core.binding.wrapper.ComponentWrapper;
import org.linkki.core.ui.wrapper.NoLabelComponentWrapper;
import org.linkki.core.ui.wrapper.VaadinComponentWrapper;
import org.linkki.core.uicreation.ComponentDefinitionCreator;
import org.linkki.core.uicreation.UiCreator;
import org.linkki.core.uicreation.layout.LayoutDefinitionCreator;
import org.linkki.core.uicreation.layout.LinkkiLayout;
import org.linkki.core.uicreation.layout.LinkkiLayoutDefinition;
import org.linkki.framework.ui.component.Headline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Consumer;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(ElementType.TYPE)
@LinkkiLayout(UIHeadLine.HeadlineLayoutDefinitionCreator.class)
@LinkkiComponent(UIHeadLine.HeadlineComponentDefinitionCreator.class)
@LinkkiAspect(UIHeadLine.HeadlineTitleAspectCreator.class)
public @interface UIHeadLine {

    String fixedTitle() default "";

    class HeadlineComponentDefinitionCreator implements ComponentDefinitionCreator<UIHeadLine> {

        @Override
        public LinkkiComponentDefinition create(UIHeadLine uiHeadLine, AnnotatedElement annotatedElement) {
            return pmo -> new Headline();
        }
    }

    class HeadlineLayoutDefinitionCreator implements LayoutDefinitionCreator<UIHeadLine> {

        @Override
        public LinkkiLayoutDefinition create(UIHeadLine uiHeadLine, AnnotatedElement annotatedElement) {
            return (parent, pmo, bindingContext) -> {
                Headline headline = (Headline)parent;
                UiCreator.createUiElements(pmo, bindingContext, c -> new NoLabelComponentWrapper((Component)c))
                        .map(VaadinComponentWrapper::getComponent)
                        .forEach(c -> headline.getContent().add(c));
            };
        }
    }

    class HeadlineTitleAspectCreator implements AspectDefinitionCreator<UIHeadLine> {

        @Override
        public LinkkiAspectDefinition create(UIHeadLine uiHeadLine) {
            return new HeadlineTitleAspectDefinition(uiHeadLine.fixedTitle());
        }
    }

    class HeadlineTitleAspectDefinition extends ModelToUiAspectDefinition<String> {

        private final String fixedTitle;

        public HeadlineTitleAspectDefinition(String fixedTitle) {
            this.fixedTitle = fixedTitle;
        }

        @Override
        public Aspect<String> createAspect() {
            if (StringUtils.isEmpty(fixedTitle)) {
                return Aspect.of(Headline.HEADER_TITLE);
            } else {
                return Aspect.of(Headline.HEADER_TITLE, fixedTitle);
            }
        }

        @Override
        public Consumer<String> createComponentValueSetter(ComponentWrapper componentWrapper) {
            Headline headline = (Headline)componentWrapper.getComponent();
            return headline::setTitle;
        }
    }
}
