import 'construct-style-sheets-polyfill';
import { unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin/register-styles';

const createLinkReferences = (css, target) => {
  // Unresolved urls are written as '@import url(text);' to the css
  // [0] is the full match
  // [1] matches the media query
  // [2] matches the url
  const importMatcher = /(?:@media\s(.+?))?(?:\s{)?\@import\surl\((.+?)\);(?:})?/g;
  
  var match;
  var styleCss = css;
  
  // For each external url import add a link reference
  while((match = importMatcher.exec(css)) !== null) {
    styleCss = styleCss.replace(match[0], "");
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = match[2];
    if (match[1]) {
      link.media = match[1];
    }
    // For target document append to head else append to target
    if (target === document) {
      document.head.appendChild(link);
    } else {
      target.appendChild(link);
    }
  };
  return styleCss;
};

// target: Document | ShadowRoot
export const injectGlobalCss = (css, target, first) => {
  if(target === document) {
    const hash = getHash(css);
    if (window.Vaadin.theme.injectedGlobalCss.indexOf(hash) !== -1) {
      return;
    }
    window.Vaadin.theme.injectedGlobalCss.push(hash);
  }
  const sheet = new CSSStyleSheet();
  sheet.replaceSync(createLinkReferences(css,target));
  if (first) {
    target.adoptedStyleSheets = [sheet, ...target.adoptedStyleSheets];
  } else {
    target.adoptedStyleSheets = [...target.adoptedStyleSheets, sheet];
  }
};
import stylesCss from 'themes/linkki/styles.css?inline';
import { utility } from '@vaadin/vaadin-lumo-styles/utility.js';
import errorMessageCss from 'themes/linkki/components/error-message.css?inline';
import inputFieldFocusRingCss from 'themes/linkki/components/input-field-focus-ring.css?inline';
import inputFieldHoverCss from 'themes/linkki/components/input-field-hover.css?inline';
import vaadinAccordionPanelCss from 'themes/linkki/components/vaadin-accordion-panel.css?inline';
import vaadinButtonCss from 'themes/linkki/components/vaadin-button.css?inline';
import vaadinCheckboxCss from 'themes/linkki/components/vaadin-checkbox.css?inline';
import vaadinComboBoxCss from 'themes/linkki/components/vaadin-combo-box.css?inline';
import vaadinDatePickerCss from 'themes/linkki/components/vaadin-date-picker.css?inline';
import vaadinDialogOverlayCss from 'themes/linkki/components/vaadin-dialog-overlay.css?inline';
import vaadinFormItemCss from 'themes/linkki/components/vaadin-form-item.css?inline';
import vaadinFormLayoutCss from 'themes/linkki/components/vaadin-form-layout.css?inline';
import vaadinGridCss from 'themes/linkki/components/vaadin-grid.css?inline';
import vaadinInputContainerCss from 'themes/linkki/components/vaadin-input-container.css?inline';
import vaadinNotificationCardCss from 'themes/linkki/components/vaadin-notification-card.css?inline';
import vaadinPasswordFieldCss from 'themes/linkki/components/vaadin-password-field.css?inline';
import vaadinRadioButtonCss from 'themes/linkki/components/vaadin-radio-button.css?inline';
import vaadinTextAreaCss from 'themes/linkki/components/vaadin-text-area.css?inline';
import vaadinTextFieldCss from 'themes/linkki/components/vaadin-text-field.css?inline';

window.Vaadin = window.Vaadin || {};
window.Vaadin.theme = window.Vaadin.theme || {};
window.Vaadin.theme.injectedGlobalCss = [];

/**
 * Calculate a 32 bit FNV-1a hash
 * Found here: https://gist.github.com/vaiorabbit/5657561
 * Ref.: http://isthe.com/chongo/tech/comp/fnv/
 *
 * @param {string} str the input value
 * @returns {string} 32 bit (as 8 byte hex string)
 */
function hashFnv32a(str) {
  /*jshint bitwise:false */
  let i, l, hval = 0x811c9dc5;

  for (i = 0, l = str.length; i < l; i++) {
    hval ^= str.charCodeAt(i);
    hval += (hval << 1) + (hval << 4) + (hval << 7) + (hval << 8) + (hval << 24);
  }

  // Convert to 8 digit hex string
  return ("0000000" + (hval >>> 0).toString(16)).substr(-8);
}

/**
 * Calculate a 64 bit hash for the given input.
 * Double hash is used to significantly lower the collision probability.
 *
 * @param {string} input value to get hash for
 * @returns {string} 64 bit (as 16 byte hex string)
 */
function getHash(input) {
  let h1 = hashFnv32a(input); // returns 32 bit (as 8 byte hex string)
  return h1 + hashFnv32a(h1 + input); 
}
export const applyTheme = (target) => {
  
  injectGlobalCss(stylesCss.toString(), target);
    
  
  if (!document['_vaadintheme_linkki_componentCss']) {
    registerStyles(
      'error-message',
      unsafeCSS(errorMessageCss.toString())
    );
    registerStyles(
      'input-field-focus-ring',
      unsafeCSS(inputFieldFocusRingCss.toString())
    );
    registerStyles(
      'input-field-hover',
      unsafeCSS(inputFieldHoverCss.toString())
    );
    registerStyles(
      'vaadin-accordion-panel',
      unsafeCSS(vaadinAccordionPanelCss.toString())
    );
    registerStyles(
      'vaadin-button',
      unsafeCSS(vaadinButtonCss.toString())
    );
    registerStyles(
      'vaadin-checkbox',
      unsafeCSS(vaadinCheckboxCss.toString())
    );
    registerStyles(
      'vaadin-combo-box',
      unsafeCSS(vaadinComboBoxCss.toString())
    );
    registerStyles(
      'vaadin-date-picker',
      unsafeCSS(vaadinDatePickerCss.toString())
    );
    registerStyles(
      'vaadin-dialog-overlay',
      unsafeCSS(vaadinDialogOverlayCss.toString())
    );
    registerStyles(
      'vaadin-form-item',
      unsafeCSS(vaadinFormItemCss.toString())
    );
    registerStyles(
      'vaadin-form-layout',
      unsafeCSS(vaadinFormLayoutCss.toString())
    );
    registerStyles(
      'vaadin-grid',
      unsafeCSS(vaadinGridCss.toString())
    );
    registerStyles(
      'vaadin-input-container',
      unsafeCSS(vaadinInputContainerCss.toString())
    );
    registerStyles(
      'vaadin-notification-card',
      unsafeCSS(vaadinNotificationCardCss.toString())
    );
    registerStyles(
      'vaadin-password-field',
      unsafeCSS(vaadinPasswordFieldCss.toString())
    );
    registerStyles(
      'vaadin-radio-button',
      unsafeCSS(vaadinRadioButtonCss.toString())
    );
    registerStyles(
      'vaadin-text-area',
      unsafeCSS(vaadinTextAreaCss.toString())
    );
    registerStyles(
      'vaadin-text-field',
      unsafeCSS(vaadinTextFieldCss.toString())
    );
    
    document['_vaadintheme_linkki_componentCss'] = true;
  }
  injectGlobalCss(utility.cssText, target, true);

}
