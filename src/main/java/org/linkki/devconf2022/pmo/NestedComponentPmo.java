package org.linkki.devconf2022.pmo;

import com.vaadin.flow.theme.lumo.LumoUtility;
import org.linkki.core.defaults.ui.aspects.types.AvailableValuesType;
import org.linkki.core.pmo.ModelObject;
import org.linkki.core.ui.aspects.annotation.BindPlaceholder;
import org.linkki.core.ui.aspects.annotation.BindStyleNames;
import org.linkki.core.ui.element.annotation.UIComboBox;
import org.linkki.core.ui.element.annotation.UITextField;
import org.linkki.core.ui.layout.annotation.UIHorizontalLayout;
import org.linkki.core.ui.layout.annotation.UISection;
import org.linkki.core.ui.layout.annotation.UIVerticalLayout;
import org.linkki.core.ui.nested.annotation.UINestedComponent;

import java.util.List;

@UISection
public class NestedComponentPmo {

    @UINestedComponent(position = 10, label = "Location")
    public AddressPmo getLocation() {
        return new AddressPmo(new Address());
    }

    /**
     * A reusable PMO to display {@link Address}.
     *
     * Be careful of the paddings and spacings when using @UIVerticalLayout as nested component.
     */
    @BindStyleNames(LumoUtility.MaxWidth.SCREEN_SMALL)
    @UIVerticalLayout(padding = false, spacing = false)
    public static class AddressPmo {

        @ModelObject
        private final Address address;

        public AddressPmo(Address address) {
            this.address = address;
        }

        /**
         * Nested components can again contain other nested components.
         */
        @UINestedComponent(position = 10)
        public StreetNoPmo getStreetNo() {
            return new StreetNoPmo(address);
        }

        @UINestedComponent(position = 20)
        public ZipCodeCityPmo getZipCodeCity() {
            return new ZipCodeCityPmo(address);
        }

        /**
         * Nested components can be combined with "normal" UI annotations.
         * <p>
         * Placeholders can be used instead of labels if the content is clear as soon as a value is present.
         * However, required indicator would not show properly at the moment.
         */
        @BindPlaceholder("Land")
        @UIComboBox(position = 30, label = "", modelAttribute = "country", content = AvailableValuesType.DYNAMIC)
        public void country() {
            // model binding
        }

        public List<String> getCountryAvailableValues() {
            return List.of("Germany", "Switzerland", "Austria");
        }

        @UIHorizontalLayout
        public class ZipCodeCityPmo {

            @ModelObject
            private final Address address;

            public ZipCodeCityPmo(Address address) {
                this.address = address;
            }

            @BindPlaceholder("PLZ")
            @UITextField(position = 10, label = "", modelAttribute = "zipCode", width = "6em")
            public void zipCode() {
                // model binding
            }

            @BindPlaceholder("Ort")
            @UITextField(position = 20, label = "", modelAttribute = "city")
            public void city() {
                // model binding
            }
        }

        @UIHorizontalLayout
        public class StreetNoPmo {

            @ModelObject
            private final Address address;

            public StreetNoPmo(Address address) {
                this.address = address;
            }

            @BindPlaceholder("Straße")
            @UITextField(position = 10, label = "", modelAttribute = "street")
            public void street() {
                // model binding
            }

            @BindPlaceholder("Nr.")
            @UITextField(position = 20, width = "5em", label = "", modelAttribute = "no")
            public void no() {
                // model binding
            }
        }
    }

    public static class Address {

        private String street;
        private String no;
        private String zipCode;
        private String city;
        private String country;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
