/**
 Copyright (c) 2015, 2017, Oracle and/or its affiliates.
 The Universal Permissive License (UPL), Version 1.0
 */
define(
  ['ojs/ojcore', 'knockout', 'jquery'], function (oj, ko, $) {
    'use strict';

    function ExampleComponentModel(context) {
      var self = this;
      self.composite = context.element;
      //Example observable
      self.symbol = ko.observable('x');
      self.factor = ko.observable('2');
      self.multiDiv = ko.observable('2');
      self.result = ko.observable('');
      self.calculations = [];

      context.props.then(function (propertyMap) {
        //Store a reference to the properties for any later use
        self.properties = propertyMap;

        //Parse your component properties here
        self.by = [].concat(self.properties.by);
        self.from = self.properties.from;
        self.to = self.properties.to;
        self.calc = {
          by: self.by,
          from: self.from,
          to: self.to,
        };
        console.log("Sending calc request: " + JSON.stringify(self.calc), self.calc);

        fetch('http://localhost:8080/tt.service/api/times', {
          method: 'POST',
          mode: 'cors',
          headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
          },
          body: JSON.stringify(self.calc)
        })
          .then(function(response) { return response.json(); })
          .then(function(json) {
            console.log("Got response: " + JSON.stringify(json, null, 2));
            self.calculations = json;
          });
      });
    };

    //Lifecycle methods - uncomment and implement if necessary 
    //ExampleComponentModel.prototype.activated = function(context){
    //};

    // ExampleComponentModel.prototype.attached = function(context){
    // };

    ExampleComponentModel.prototype.bindingsApplied = function(context){
    };

    //ExampleComponentModel.prototype.detached = function(context){
    //};

    return ExampleComponentModel;
  });