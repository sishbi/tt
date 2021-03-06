/*
 * Copyright (c) 2018 Simon Billingsley. All rights reserved.
 */
/*
 * Your application specific code will go here
 */
define(["ojs/ojcore", "knockout", "ojs/ojrouter", "ojs/ojknockout", "ojs/ojarraytabledatasource"],
  function(oj, ko) {
     function ControllerViewModel() {
       var self = this;

      // Media queries for repsonsive layouts
      var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
      self.smScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);

       // Router setup
       self.router = oj.Router.rootInstance;
       self.router.configure({
         "tables": {label: "Home", isDefault: true}
       });
      oj.Router.defaults["urlAdapter"] = new oj.Router.urlParamAdapter();

      // Navigation setup
      var navData = [{
        name: "Home", id: "tables",
      }];
      self.navDataSource = new oj.ArrayTableDataSource(navData, {idAttribute: "id"});

      // Header
      // Application Name used in Branding Area
      self.appName = ko.observable("Times Table Master");

      // Footer
      function footerLink(name, id, linkTarget) {
        this.name = name;
        this.linkId = id;
        this.linkTarget = linkTarget;
      }
      self.footerLinks = ko.observableArray([
      ]);
     }

     return new ControllerViewModel();
  }
);
