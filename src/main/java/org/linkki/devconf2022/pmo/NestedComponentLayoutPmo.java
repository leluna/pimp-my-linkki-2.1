package org.linkki.devconf2022.pmo;

import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.linkki.core.defaults.ui.aspects.types.AvailableValuesType;
import org.linkki.core.defaults.ui.aspects.types.RequiredType;
import org.linkki.core.pmo.ModelObject;
import org.linkki.core.ui.aspects.annotation.BindPlaceholder;
import org.linkki.core.ui.aspects.annotation.BindStyleNames;
import org.linkki.core.ui.element.annotation.UIComboBox;
import org.linkki.core.ui.element.annotation.UIDateField;
import org.linkki.core.ui.element.annotation.UITextArea;
import org.linkki.core.ui.layout.annotation.UIHorizontalLayout;
import org.linkki.core.ui.layout.annotation.UISection;
import org.linkki.core.ui.nested.annotation.UINestedComponent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@BindStyleNames(LumoUtility.FlexWrap.WRAP)
@UIHorizontalLayout
public class NestedComponentLayoutPmo {

    @ModelObject
    private final IncidenceReport incidenceReport;

    public NestedComponentLayoutPmo() {
        this.incidenceReport = new IncidenceReport();
    }

    @BindStyleNames(LumoUtility.MaxWidth.SCREEN_SMALL)
    @UINestedComponent(position = 10)
    public IncidenceContentPmo getIncidence() {
        return new IncidenceContentPmo(incidenceReport);
    }

    @BindStyleNames(LumoUtility.MaxWidth.SCREEN_SMALL)
    @UINestedComponent(position = 20)
    public TimeLocationPmo getTimeLocation() {
        return new TimeLocationPmo(incidenceReport);
    }

    @UISection
    public static class IncidenceContentPmo {
        @ModelObject
        private final IncidenceReport incidenceReport;

        public IncidenceContentPmo(IncidenceReport incidenceReport) {
            this.incidenceReport = incidenceReport;
        }

        @BindPlaceholder("Bitte wählen")
        @UIComboBox(position = 10, label = "Schadenart", modelAttribute = "incidenceKind",
                content = AvailableValuesType.ENUM_VALUES_EXCL_NULL,
                required = RequiredType.REQUIRED)
        public IncidenceKind getIncidenceKind() {
            return incidenceReport.getIncidenceKind();
        }

        public void setIncidenceKind(IncidenceKind incidenceKind) {
            incidenceReport.setIncidenceKind(incidenceKind);
            if (!incidenceKind.getCauses().contains(incidenceReport.getCause())) {
                incidenceReport.setCause(null);
            }
        }

        @BindPlaceholder
        @UIComboBox(position = 20, label = "Ursache", modelAttribute = "cause", width = "20rem",
                content = AvailableValuesType.DYNAMIC,
                required = RequiredType.REQUIRED)
        public void cause() {
            // model binding
        }

        public List<String> getCauseAvailableValues() {
            return Optional.ofNullable(incidenceReport.getIncidenceKind())
                    .map(IncidenceKind::getCauses)
                    .orElse(List.of());
        }

        public String getCausePlaceholder() {
            return getIncidenceKind() != null ? "Bitte wählen" : "Bitte zuerst Schadenart auswählen";
        }

        @BindPlaceholder("Wie ist der Schaden entstanden, und welche Folgen hat er mit sich gebracht?")
        @UITextArea(position = 30, label = "Hergang", modelAttribute = "description", height = "5rem")
        public void getDescription() {
            // model binding
        }

    }

    @UISection
    public static class TimeLocationPmo {

        @ModelObject
        private final IncidenceReport incidenceReport;

        public TimeLocationPmo(IncidenceReport incidenceReport) {
            this.incidenceReport = incidenceReport;
        }

        @UINestedComponent(position = 40, label = "Schadenzeitpunkt")
        public OccurrenceTimePmo occurrenceTime() {
            return new OccurrenceTimePmo(incidenceReport);
        }

        @UINestedComponent(position = 50, label = "Schadenort")
        public NestedComponentPmo.AddressPmo getLocation() {
            return new NestedComponentPmo.AddressPmo(incidenceReport.getLocation());
        }
    }

    @UIHorizontalLayout
    public static class OccurrenceTimePmo {

        @ModelObject
        private IncidenceReport incidenceReport;

        public OccurrenceTimePmo(IncidenceReport incidenceReport) {
            this.incidenceReport = incidenceReport;
        }

        @BindPlaceholder("TT.MM.JJJJ")
        @UIDateField(position = 10, label = "", modelAttribute = "occurrenceDate", required = RequiredType.REQUIRED)
        public void date() {
            // model binding
        }

        @BindPlaceholder("hh:mm")
        @VaadinComponentPmo.UITimeField(position = 20, label = "", modelAttribute = "occurrenceTime")
        public void time() {
            // model binding
        }
    }

    public static class IncidenceReport {

        private IncidenceKind incidenceKind;
        private String cause;
        private String description;
        private LocalDate occurrenceDate;
        private LocalTime occurrenceTime;
        private NestedComponentPmo.Address location;

        public IncidenceReport() {
            this.location = new NestedComponentPmo.Address();
        }

        public LocalDate getOccurrenceDate() {
            return occurrenceDate;
        }

        public void setOccurrenceDate(LocalDate occurrenceDate) {
            this.occurrenceDate = occurrenceDate;
        }

        public LocalTime getOccurrenceTime() {
            return occurrenceTime;
        }

        public void setOccurrenceTime(LocalTime occurenceTime) {
            this.occurrenceTime = occurenceTime;
        }

        public NestedComponentPmo.Address getLocation() {
            return location;
        }

        public void setLocation(NestedComponentPmo.Address location) {
            this.location = location;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public IncidenceKind getIncidenceKind() {
            return incidenceKind;
        }

        public void setIncidenceKind(IncidenceKind incidenceKind) {
            this.incidenceKind = incidenceKind;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }
    }

    public enum IncidenceKind {

        FEUER("Feuer/Überspannung",
                "Offenes Feuer",
                "Sengschäden",
                "Blitzeinschlag",
                "Ausfall einer Gefriereinheit",
                "Sonstiges/Unbekannt"),
        WASSERSCHADEN("Wasserschaden",
                "Rohrbruch",
                "Defekte Wasch-/Geschirrspülmaschine",
                "Sonstiges/Unbekannt"),
        FAHRRAD("Fahrrad",
                "Diebstahl",
                "Schädigung");

        private final String name;
        private final String[] causes;

        IncidenceKind(String name, String... causes) {
            this.name = name;
            this.causes = causes;
        }

        public List<String> getCauses() {
            return Arrays.asList(causes);
        }

        public String getName() {
            return name;
        }
    }
}
