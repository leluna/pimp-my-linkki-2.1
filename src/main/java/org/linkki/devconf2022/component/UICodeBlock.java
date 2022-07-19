package org.linkki.devconf2022.component;

import com.vaadin.flow.component.HasText;
import org.linkki.core.binding.descriptor.aspect.Aspect;
import org.linkki.core.binding.descriptor.aspect.LinkkiAspectDefinition;
import org.linkki.core.binding.descriptor.aspect.annotation.AspectDefinitionCreator;
import org.linkki.core.binding.descriptor.aspect.annotation.LinkkiAspect;
import org.linkki.core.binding.descriptor.aspect.base.CompositeAspectDefinition;
import org.linkki.core.binding.descriptor.aspect.base.ModelToUiAspectDefinition;
import org.linkki.core.binding.descriptor.property.annotation.BoundPropertyCreator;
import org.linkki.core.binding.descriptor.property.annotation.LinkkiBoundProperty;
import org.linkki.core.binding.uicreation.LinkkiComponent;
import org.linkki.core.binding.uicreation.LinkkiComponentDefinition;
import org.linkki.core.binding.wrapper.ComponentWrapper;
import org.linkki.core.ui.aspects.LabelAspectDefinition;
import org.linkki.core.uicreation.ComponentDefinitionCreator;
import org.linkki.core.uicreation.LinkkiPositioned;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.function.Consumer;

@LinkkiPositioned
@LinkkiBoundProperty(BoundPropertyCreator.SimpleMemberNameBoundPropertyCreator.class)
@LinkkiComponent(UICodeBlock.CodeBlockComponentDefinitionCreator.class)
@LinkkiAspect(UICodeBlock.CodeBlockAspectDefinitionCreator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UICodeBlock {

    @LinkkiPositioned.Position
    int position();

    String label() default "";

    class CodeBlockComponentDefinitionCreator implements ComponentDefinitionCreator<UICodeBlock> {

        @Override
        public LinkkiComponentDefinition create(UICodeBlock uiCodeBlock, AnnotatedElement annotatedElement) {
            return pmo -> new CodeBlock();
        }
    }

    class CodeBlockAspectDefinitionCreator implements AspectDefinitionCreator<UICodeBlock> {

        @Override
        public LinkkiAspectDefinition create(UICodeBlock uiCodeBlock) {
            return new CompositeAspectDefinition(new LabelAspectDefinition(uiCodeBlock.label()),
                    new TextAsValueAspectDefinitionCreator());
        }
    }

    class TextAsValueAspectDefinitionCreator extends ModelToUiAspectDefinition {

        @Override
        public Aspect createAspect() {
            return Aspect.of("");
        }

        @Override
        public Consumer createComponentValueSetter(ComponentWrapper componentWrapper) {
            HasText component = (HasText)componentWrapper.getComponent();
            return v -> component.setText(String.valueOf(v));
        }
    }

}
