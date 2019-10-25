package OpenJFXLiteDemo;

import com.dukescript.api.javafx.beans.FXBeanInfo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class ControllerViewModel implements FXBeanInfo.Provider {

    /*
     function ControllerViewModel() {
       var self = this;

      // Media queries for repsonsive layouts
      var smQuery = ResponsiveUtils.getFrameworkQuery(ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
      self.smScreen = ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);

      // Header
      // Application Name used in Branding Area
      self.appName = ko.observable("App Name");
      // User Info used in Global Navigation area
      self.userLogin = ko.observable("john.hancock@oracle.com");

      // Footer
      function footerLink(name, id, linkTarget) {
        this.name = name;
        this.linkId = id;
        this.linkTarget = linkTarget;
      }
      self.footerLinks = ko.observableArray([
        new footerLink('About Oracle', 'aboutOracle', 'http://www.oracle.com/us/corporate/index.html#menu-about'),
        new footerLink('Contact Us', 'contactUs', 'http://www.oracle.com/us/corporate/contact/index.html'),
        new footerLink('Legal Notices', 'legalNotices', 'http://www.oracle.com/us/legal/index.html'),
        new footerLink('Terms Of Use', 'termsOfUse', 'http://www.oracle.com/us/legal/terms/index.html'),
        new footerLink('Your Privacy Rights', 'yourPrivacyRights', 'http://www.oracle.com/us/legal/privacy/index.html')
      ]);
     }

     return new ControllerViewModel();
     */

    private BooleanProperty smScreen = new SimpleBooleanProperty(this, "smScreen", false);
    private StringProperty appName = new SimpleStringProperty(this, "appName", "App Name");
    private StringProperty userLogin = new SimpleStringProperty(this, "userLogin", "john.hancock@oracle.com");
    private ListProperty<String> footerLinks = new SimpleListProperty<>(this, "footerLinks", FXCollections.observableArrayList());
    private FXBeanInfo info;

    public ControllerViewModel() {
        info = FXBeanInfo
                .newBuilder(this)
                .property(smScreen)
                .property(appName)
                .property(userLogin)
                .property(footerLinks)
                .build();
    }

    @Override
    public FXBeanInfo getFXBeanInfo() {
        return info;
    }

}
