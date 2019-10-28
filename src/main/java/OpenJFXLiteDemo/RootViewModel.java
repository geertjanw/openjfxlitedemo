package OpenJFXLiteDemo;

import com.dukescript.api.javafx.beans.ActionDataEvent;
import com.dukescript.api.javafx.beans.FXBeanInfo;
import java.util.Arrays;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class RootViewModel implements FXBeanInfo.Provider {

    /*
     function RootViewModel() {
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

     return new RootViewModel();
     */
    private BooleanProperty smScreen = new SimpleBooleanProperty(this, "smScreen", false);
    private StringProperty appName = new SimpleStringProperty(this, "appName", "App Name");
    private StringProperty userLogin = new SimpleStringProperty(this, "userLogin", "john.hancock@oracle.com");
    private ListProperty<FooterLink> footerLinks = new SimpleListProperty<>(this, "footerLinks", FXCollections.observableArrayList());
    private FXBeanInfo info;

    public RootViewModel() {
        info = FXBeanInfo
                .newBuilder(this)
                .property(smScreen)
                .property(appName)
                .property(userLogin)
                .property(footerLinks)
                .build();
        footerLinks.addAll(Arrays.asList(
                new FooterLink("About Oracle", "aboutOracle", "http://www.oracle.com/us/corporate/index.html#menu-about"),
                new FooterLink("Contact Us", "contactUs", "http://www.oracle.com/us/corporate/contact/index.html"),
                new FooterLink("Legal Notices", "legalNotices", "http://www.oracle.com/us/legal/index.html"),
                new FooterLink("Terms Of Use", "termsOfUse", "http://www.oracle.com/us/legal/terms/index.html"),
                new FooterLink("Your Privacy Rights", "yourPrivacyRights", "http://www.oracle.com/us/legal/privacy/index.html")
        ));
    }

    @Override
    public FXBeanInfo getFXBeanInfo() {
        return info;
    }

    private static class FooterLink implements FXBeanInfo.Provider {

        private StringProperty name = new SimpleStringProperty(this, "name");
        private StringProperty linkId = new SimpleStringProperty(this, "linkId");
        private StringProperty linkTarget = new SimpleStringProperty(this, "linkTarget");
        private FXBeanInfo info;

        public FooterLink(String name, String linkId, String linkTarget) {
            this.name.set(name);
            this.linkId.set(linkId);
            this.linkTarget.set(linkTarget);
            info = FXBeanInfo
                    .newBuilder(this)
                    .property(this.name)
                    .property(this.linkId)
                    .property(this.linkTarget)
                    .action("onClick", this::onClick)
                    .build();
        }
        
        void onClick(ActionDataEvent ade) {
            System.out.println("hello");
        }

        @Override
        public FXBeanInfo getFXBeanInfo() {
            return info;
        }

    }

}
