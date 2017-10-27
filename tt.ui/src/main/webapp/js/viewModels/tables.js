/**
 * Copyright (c) 2014, 2017, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your about ViewModel code goes here
 */
define(["ojs/ojcore", "knockout", "jquery", "ojs/ojbutton", "ojs/ojcheckboxset", "ojs/ojradioset",
        "ojs/ojknockout", "ojs/ojinputtext", "ojs/ojvalidation-number", "ojs/ojlabel"],
 function(oj, ko, $) {
  
    function TimesViewModel() {
      var self = this;
      // calculation input
      self.level = ko.observable("basic");
      self.timesByBasic = ko.observableArray([]);
      self.timesByInter = ko.observableArray([]);
      self.timesByAdv = ko.observableArray([]);
      self.numQuestions = ko.observable("12");
      self.operation = ko.observable("*");
      self.from = 1;
      self.to = 12;
      self.timesBy = [];
      // calculation response
      self.calculations = [];
      // position within calculation array (starting from zero)
      self.position = 0;
      self.numAnswers = ko.observable(1);
      // whether the questions are running/complete or not
      self.running = ko.observable(false);
      // question symbols
      self.multiplySymbol = "\u00D7";
      self.divideSymbol = "\u00F7";
      // question: <factor1> <multiply/divide> <factor2> = <answer>
      self.symbol = ko.observable("");
      self.factor1 = ko.observable("");
      self.factor2 = ko.observable("");
      self.answer = ko.observable("");
      self.rawAnswer = ko.observable("");
      // text field for answer
      self.userAnswer = "";
      self.calcAnswer = "";
      self.answerShouldBe = ko.observable("");
      // is the answer correct?
      self.answered = ko.observable(false);
      self.correct = ko.observable("");
      self.isComplete = ko.observable(false);
      self.correctAnswers = 0;
      self.resBegin = "You answered ";
      self.resMiddle = " out of ";
      self.resEnd = " questions correctly";
      self.resultTxt = ko.observable("");
      self.incorrectResultsTxt = ko.observable("");
      self.incorrectAnswers = [];

      self.isRunning = ko.computed(function() {
        return self.running() === true;
      });
      self.notRunning = ko.computed(function() {
        return self.running() === false;
      });
      self.notBasic = ko.computed(function() {
        return self.level() !== "basic";
      });
      self.notInter = ko.computed(function() {
        return self.level() !== "intermediate";
      });
      self.notAdv = ko.computed(function() {
        return self.level() !== "advanced";
      });

      self.noStart = ko.computed(function() {
        let numBasic = self.timesByBasic().length;
        let numInter = self.timesByInter().length;
        let numAdv = self.timesByAdv().length;
        console.log("noStart: level=" + self.level() + ", numBasic=" + numBasic +
                    ", numInter=" + numInter + ", numAdv=" + numAdv);
        return (self.level() === "basic" && numBasic === 0 ||
                self.level() === "intermediate" && numInter === 0 ||
                self.level() === "advanced" && numAdv === 0);
      });

      self.setAll = function() {
        console.log("Mix of All");
        self.timesByBasic(["2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]);
        return true;
      };

      self.setNone = function() {
        console.log("Set None");
        self.timesByBasic([]);
        self.timesByInter([]);
        self.timesByAdv([]);
        return true;
      };

      self.numConverter =
        oj.Validation.converterFactory(oj.ConverterFactory.CONVERTER_TYPE_NUMBER)
          .createConverter({
            "minimumFractionDigits": 0,
            "maximumFractionDigits": 0,
            "minimumIntegerDigits": 1,
            "style": "decimal",
            "useGrouping": false
          });

      self.checkAnswer = function(event) {
        if (event.defaultPrevented) {
          return; // Do nothing if the event was already processed
        }
        console.log("Key: '" + event.key + "'");
        switch (event.key) {
        case "0":
        case "1":
        case "2":
        case "3":
        case "4":
        case "5":
        case "6":
        case "7":
        case "8":
        case "9":
        case ".":
        case "Enter":
        case "Backspace":
          break;
        default:
          event.preventDefault();
        }
      };

      self.questionAnswered = function(event) {
        console.log("questionAnswered: answer=", event.detail.value);
        if (event.detail.value !== "") {
          self.userAnswer = parseFloat(event.detail.value);
          self.answered(true);
          console.log("userAnswer=" + self.userAnswer + ", calcAnswer=" + self.calcAnswer);
          let nextTimeout = 500;
          if (self.userAnswer === self.calcAnswer) {
            self.correctAnswers++;
            self.correct("oj-fwk-icon-status-confirmation");
            self.answerShouldBe("");
          } else {
            self.correct("oj-fwk-icon-status-error");
            self.answerShouldBe(self.calcAnswer);
            nextTimeout = 1000;
            self.incorrectAnswers.push({calc: self.currentCalc, symbol: self.symbol(), userAnswer: self.userAnswer});
          }

          setTimeout(function() {
            self.answer("");
            self.correct("");
            self.answerShouldBe("");
            self.answered(false);
            self.nextQuestion();
          }, nextTimeout);
        }
      };

      self.nextQuestion = function() {
        $("#answer").focus();
        setTimeout(() => { $("#answer").focus(); }, 0);
        console.log("nextQuestion: position=" + self.position +
                    ", num calculations=" + self.calculations.length);
        if (self.position >= self.calculations.length) {
          self.position = 0;
        }
        let numAnswers = self.numAnswers();
        numAnswers++;
        if (numAnswers > self.numQuestions()) {
          self.resultTxt(self.resBegin + self.correctAnswers + self.resMiddle + self.numQuestions() + self.resEnd);
          self.incorrectResultsTxt("");
          let incorrectTxt = "";
          self.incorrectAnswers.forEach((inc) => {
            console.log("Incorrect: " + JSON.stringify(inc));
            incorrectTxt += "<p>" + inc.calc.times + " " + inc.symbol + " "
                         + inc.calc.by + " = " + inc.calc.answer
                         + " (and not: " + inc.userAnswer + ")</p>";
          });
          self.incorrectResultsTxt(incorrectTxt);
          self.isComplete(true);
          return;
        }
        self.numAnswers(numAnswers);
        self.currentCalc = self.calculations[self.position++];
        console.log("nextQuestion: calc=" + JSON.stringify(self.currentCalc, null, 2));
        self.factor1(self.currentCalc.times);
        self.factor2(self.currentCalc.by);
        self.symbol(self.currentCalc.op === "*" ? self.multiplySymbol : self.divideSymbol);
        self.userAnswer = 0;
        self.calcAnswer = self.currentCalc.answer;
      };

      self.endQuestions = function() {
        console.log("endQuestions");
        self.running(false);
        self.isComplete(false);
      };

      self.getCalculations = function() {
        if (self.level() === "basic") {
          self.to = 12;
          self.timesBy = self.timesByBasic();
        } else if (self.level() === "intermediate") {
          self.to = 50;
          self.timesBy = self.timesByInter();
        } else if (self.level() === "advanced") {
          self.to = 12;
          self.timesBy = self.timesByAdv();
        }
        self.op = self.operation();
        console.log("getCalculation, by=" + self.timesBy + ", from=" + self.from +
                    ", to=" + self.to + ", op=" + self.op);
        let calcReq = {
          by: self.timesBy,
          from: self.from,
          to: self.to,
          op: self.op
        };
        console.log("Sending calc request: " + JSON.stringify(calcReq));
        self.running(true);

        fetch("http://localhost:8080/tt.service/api/times", {
          method: "POST",
          mode: "cors",
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
          },
          body: JSON.stringify(calcReq)
        })
          .then(function(response) { return response.json(); })
          .then(function(json) {
            console.log("Got calc response: length=" + json.length, json);
            self.calculations = json;
            self.correctAnswers = 0;
            self.numAnswers(0);
            self.position = 0;
            self.incorrectAnswers = [];
            self.nextQuestion();
          });
      };

      // Below are a subset of the ViewModel methods invoked by the ojModule binding
      // Please reference the ojModule jsDoc for additional available methods.

      /**
       * Optional ViewModel method invoked when this ViewModel is about to be
       * used for the View transition.  The application can put data fetch logic
       * here that can return a Promise which will delay the handleAttached function
       * call below until the Promise is resolved.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a "virtual" element (comment node).
       * @param {Function} info.valueAccessor - The binding"s value accessor.
       * @return {Promise|undefined} - If the callback returns a Promise, the next phase (attaching DOM) will be delayed until
       * the promise is resolved
       */
      self.handleActivated = function(info) {
        // Implement if needed
      };

      /**
       * Optional ViewModel method invoked after the View is inserted into the
       * document DOM.  The application can put logic that requires the DOM being
       * attached here.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a "virtual" element (comment node).
       * @param {Function} info.valueAccessor - The binding"s value accessor.
       * @param {boolean} info.fromCache - A boolean indicating whether the module was retrieved from cache.
       */
      self.handleAttached = function(info) {
        // Implement if needed
      };


      /**
       * Optional ViewModel method invoked after the bindings are applied on this View. 
       * If the current View is retrieved from cache, the bindings will not be re-applied
       * and this callback will not be invoked.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a "virtual" element (comment node).
       * @param {Function} info.valueAccessor - The binding"s value accessor.
       */
      self.handleBindingsApplied = function(info) {
        // Implement if needed
        $("#answer").off("keydown").on("keydown", self.checkAnswer);
      };

      /*
       * Optional ViewModel method invoked after the View is removed from the
       * document DOM.
       * @param {Object} info - An object with the following key-value pairs:
       * @param {Node} info.element - DOM element or where the binding is attached. This may be a "virtual" element (comment node).
       * @param {Function} info.valueAccessor - The binding"s value accessor.
       * @param {Array} info.cachedNodes - An Array containing cached nodes for the View if the cache is enabled.
       */
      self.handleDetached = function(info) {
        // Implement if needed
      };
    }

    /*
     * Returns a constructor for the ViewModel so that the ViewModel is constructed
     * each time the view is displayed.  Return an instance of the ViewModel if
     * only one instance of the ViewModel is needed.
     */
    return new TimesViewModel();
  }
);
