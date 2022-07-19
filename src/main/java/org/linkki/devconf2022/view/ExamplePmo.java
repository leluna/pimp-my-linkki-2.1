package org.linkki.devconf2022.view;

import com.vaadin.flow.theme.lumo.LumoUtility;
import org.linkki.core.defaults.ui.aspects.types.CaptionType;
import org.linkki.core.ui.aspects.annotation.BindStyleNames;
import org.linkki.core.ui.element.annotation.UIButton;
import org.linkki.core.ui.layout.annotation.UIVerticalLayout;
import org.linkki.core.ui.nested.annotation.UINestedComponent;
import org.linkki.devconf2022.component.UICodeBlock;
import org.linkki.devconf2022.component.UIHeadLine;
import org.linkki.devconf2022.component.UISplitLayout;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@BindStyleNames(LumoUtility.Height.FULL)
@UIVerticalLayout
public class ExamplePmo {

    private final String title;
    private final Object pmo;
    private boolean showCode;

    public ExamplePmo(String title, Object pmo) {
        this.title = title;
        this.pmo = pmo;
    }

    @UINestedComponent(position = 10)
    public ExampleHeadlinePmo getHeadline() {
        return new ExampleHeadlinePmo(title, this::isShowCode, this::setShowCode);
    }

    @BindStyleNames(LumoUtility.Overflow.AUTO)
    @UINestedComponent(position = 20)
    public ExampleWithCodePmo getContent() {
        return new ExampleWithCodePmo(pmo, this::isShowCode);
    }

    private boolean isShowCode() {
        return showCode;
    }

    private void setShowCode(boolean showCode) {
        this.showCode = showCode;
    }

    @UIHeadLine
    public static class ExampleHeadlinePmo {

        private final String title;
        private final BooleanSupplier isShowCode;
        private final Consumer<Boolean> setShowCode;

        public ExampleHeadlinePmo(String title, BooleanSupplier isShowCode, Consumer<Boolean> setShowCode) {
            this.title = title;
            this.isShowCode = isShowCode;
            this.setShowCode = setShowCode;
        }

        public String getHeaderTitle() {
            return title;
        }

        @UIButton(position = 10, captionType = CaptionType.DYNAMIC)
        public void showCode() {
            setShowCode.accept(!isShowCode.getAsBoolean());
        }

        public String getShowCodeCaption() {
            if (isShowCode.getAsBoolean()) {
                return "Hide Source Code";
            } else {
                return "Show Source Code";
            }
        }
    }

    @UISplitLayout
    public static class ExampleWithCodePmo {

        private final Object pmo;
        private final BooleanSupplier isShowCode;

        public ExampleWithCodePmo(Object pmo, BooleanSupplier isShowCode) {
            this.pmo = pmo;
            this.isShowCode = isShowCode;
        }

        @UISplitLayout.Primary
        @BindStyleNames(LumoUtility.Margin.Right.LARGE)
        @UINestedComponent(position = 10)
        public Object getPmo() {
            return pmo;
        }

        @UISplitLayout.Secondary
        @UICodeBlock(position = 20)
        public String getCode() {
            try {
                var fileName = pmo.getClass().getSimpleName() + ".java";
                var sourceFile = ResourceUtils.getFile("classpath:code/" + fileName);
                return new String(Files.readAllBytes(sourceFile.toPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public Double getSplitterPosition() {
            if (isShowCode.getAsBoolean()) {
                return Double.valueOf(50);
            } else {
                return Double.valueOf(100);
            }
        }
    }

}
