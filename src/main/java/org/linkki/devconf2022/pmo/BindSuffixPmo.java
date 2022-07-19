package org.linkki.devconf2022.pmo;

import org.linkki.core.defaults.ui.aspects.types.AlignmentType;
import org.linkki.core.ui.aspects.annotation.BindSuffix;
import org.linkki.core.ui.element.annotation.UIIntegerField;
import org.linkki.core.ui.element.annotation.UILabel;
import org.linkki.core.ui.element.annotation.UIRadioButtons;
import org.linkki.core.ui.layout.annotation.UISection;

@UISection
public class BindSuffixPmo {

    private final int base = 20000;
    private BonusInputMode bonusInputMode = BonusInputMode.ABSOLUTE;
    private int bonus = 20;

    /**
     * BindSuffix can be combined with different components, here an example with UILabel.
     */
    @BindSuffix("€")
    @UILabel(position = 10, label = "Base")
    public int getBase() {
        return base;
    }

    @UIRadioButtons(position = 20, label = "Bonus", buttonAlignment = AlignmentType.HORIZONTAL)
    public BonusInputMode getBonusInputMode() {
        return bonusInputMode;
    }

    public void setBonusInputMode(BonusInputMode bonusInputMode) {
        this.bonusInputMode = bonusInputMode;
    }

    /**
     * Dynamic suffix, combined with UIIntegerField.
     */
    @BindSuffix
    @UIIntegerField(position = 30, label = "")
    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getBonusSuffix() {
        switch (bonusInputMode) {
            case PERCENTAGE:
                return "%";
            case ABSOLUTE:
                return "€";
        }
        return "";
    }

    @BindSuffix("€")
    @UILabel(position = 40, label = "Total")
    public int getTotal() {
        return base + bonusInputMode.getBonus(bonus, base);
    }

    public enum BonusInputMode {
        PERCENTAGE(true, "percentage"), ABSOLUTE(false, "absolute");

        private final boolean isBonusPercentage;
        private final String displayName;

        BonusInputMode(boolean isBonusPercentage, String displayName) {
            this.isBonusPercentage = isBonusPercentage;
            this.displayName = displayName;
        }

        public int getBonus(int input, int base) {
            if (isBonusPercentage) {
                return base * input / 100;
            } else {
                return input;
            }
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
