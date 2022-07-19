package org.linkki.devconf2022.pmo;

import com.vaadin.flow.component.timepicker.TimePicker;
import org.linkki.core.binding.descriptor.aspect.LinkkiAspectDefinition;
import org.linkki.core.binding.descriptor.aspect.annotation.AspectDefinitionCreator;
import org.linkki.core.binding.descriptor.aspect.annotation.LinkkiAspect;
import org.linkki.core.binding.descriptor.aspect.base.CompositeAspectDefinition;
import org.linkki.core.binding.descriptor.property.annotation.LinkkiBoundProperty;
import org.linkki.core.binding.uicreation.LinkkiComponent;
import org.linkki.core.binding.uicreation.LinkkiComponentDefinition;
import org.linkki.core.pmo.ModelObject;
import org.linkki.core.ui.aspects.LabelAspectDefinition;
import org.linkki.core.ui.aspects.ValueAspectDefinition;
import org.linkki.core.ui.layout.annotation.UISection;
import org.linkki.core.uicreation.ComponentDefinitionCreator;
import org.linkki.core.uicreation.LinkkiPositioned;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.time.LocalTime;

@UISection
public class VaadinComponentPmo {

    private LocalTime time;

    @UITimeField(position = 10, label = "TimePicker")
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @LinkkiBoundProperty
    @LinkkiPositioned
    @LinkkiAspect(UITimeField.TimeFieldAspectDefinitionCreator.class)
    @LinkkiComponent(UITimeField.TimeFieldComponentDefinitionCreator.class)
    public @interface UITimeField {

        @LinkkiPositioned.Position
        int position();

        String label() default LabelAspectDefinition.DERIVED_BY_LINKKI;

        @LinkkiBoundProperty.ModelObject
        String modelObject() default ModelObject.DEFAULT_NAME;

        @LinkkiBoundProperty.ModelAttribute
        String modelAttribute() default "";

        class TimeFieldComponentDefinitionCreator implements ComponentDefinitionCreator<UITimeField> {

            @Override
            public LinkkiComponentDefinition create(UITimeField uiTimeField, AnnotatedElement annotatedElement) {
                return pmo -> new TimePicker();
            }
        }

        class TimeFieldAspectDefinitionCreator implements AspectDefinitionCreator<UITimeField> {

            @Override
            public LinkkiAspectDefinition create(UITimeField uiTimeField) {
                return new CompositeAspectDefinition(new ValueAspectDefinition(), new LabelAspectDefinition(uiTimeField.label()));
            }
        }
    }
}
