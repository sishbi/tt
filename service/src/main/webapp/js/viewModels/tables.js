/**
 * Copyright (c) 2014, 2017, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your about ViewModel code goes here
 */
define(["ojs/ojcore", "knockout", "jquery", "ojs/ojbutton", "ojs/ojcheckboxset", "ojs/ojradioset",
        "ojs/ojknockout", "ojs/ojinputtext", "ojs/ojinputnumber", "ojs/ojlabel"],
 function(oj, ko, $) {
  
    function TimesViewModel() {
      const self = this;
      // calculation input
      console.log(window.location.search);
      const urlParams = new URLSearchParams(window.location.search);
      self.host = urlParams.get("host");
      self.path = (self.host ? self.host : "") + window.location.pathname;
      self.timesByL1 = ko.observableArray([]);
      self.timesByL2 = ko.observableArray([]);
      self.timesByL3 = ko.observableArray([]);
      self.numQuestions = ko.observable("12");
      self.operation = ko.observable("*");
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
      self.startTime = 0;
      self.timer = null;
      self.now = ko.observable(0);
      self.startAnswer = 0;
      self.answerTime = 0;
      self.totalAnswerTime = 0;
      self.avgAnswerTime = ko.observable(0);
      self.starting = ko.observable(false);

      self.isRunning = ko.computed(function() {
        return self.running() === true;
      });
      self.notRunning = ko.computed(function() {
        return self.running() === false;
      });

      self.noStart = ko.computed(function() {
        if (self.starting()) {
          return true;
        }
        const numQ = parseInt(self.numQuestions());
        console.log("noStart: numQ=" + numQ);
        if (self.numQuestions() === "" || isNaN(numQ) || numQ < 1 || numQ > 100) {
          return true;
        }
        const numL1 = self.timesByL1().length;
        const numL2 = self.timesByL2().length;
        const numL3 = self.timesByL3().length;
        console.log("noStart: numL1=" + numL1 +
                    ", numL2=" + numL2 + ", numL3=" + numL3);
        return (numL1 === 0 && numL2 === 0 && numL3 === 0);
      });

      self.numQuestionsValid = ko.computed(function() {
        return [{
          type: "regExp",
          options: {
            pattern: "^([1-9]|[1-9][0-9]|[1][0][0])$",
            hint: "enter a number between 1 and 100",
            messageDetail: "You must enter a number between 1 and 100"}}];
      });

      /**
       * Format the duration as a string.
       * @param {number} startTime the starting time (as seconds since epoch)
       * @param {number} endTime the current (end) time (as seconds since epoch)
       * @param {boolean} total whether to calculate the total time
       * @returns {string} the formatted duration.
       */
      self.formatDuration = function(startTime, endTime, total) {
        let duration = Math.floor(endTime - startTime);
        const hours = Math.floor(duration / 3600);
        duration = Math.floor(duration - hours * 3600);
        const minutes = Math.floor(duration / 60);
        const seconds = Math.floor(duration - minutes * 60);

        function str_pad_left(string, pad, length) {
          return (new Array(length+1).join(pad)+string).slice(-length);
        }

        if (total) {
          return "" +
            (hours > 0 ? hours + " hour" + (hours === 1 ? "" : "s") : "") +
            (minutes > 0 ? " " + minutes + " minute" + (minutes === 1 ? "" : "s") + " and" : "") +
            " " + seconds + " seconds";
        } else {
          return (hours > 0 ? (str_pad_left(hours, "0", 2) + ":") : "") +
            (minutes > 0 ? str_pad_left(minutes, "0", 2) : "0") + ":" +
            str_pad_left(seconds, "0", 2);
        }
      };

      function getTimeNow() {
        return Math.floor(new Date().getTime() / 1000);
      }

      self.startTimer = function() {
        if (self.timer) {
          self.stopTimer();
        }
        self.timer = setInterval(function () {
          self.now(getTimeNow());
        }, 1000);
        console.log("Started timer...", self.timer);
      };

      self.stopTimer = function() {
        console.log("Stopping timer...", self.timer);
        if (self.timer) {
          clearInterval(self.timer);
          self.timer = null;
        }
      };

      self.durationTxt = ko.computed(function() {
        return self.formatDuration(self.startTime, self.now(), false);
      });

      self.totalDurationTxt = ko.computed(function() {
        return self.formatDuration(self.startTime, self.now(), true);
      });

      self.setNone = function() {
        console.log("Set None");
        self.timesByL1([]);
        self.timesByL2([]);
        self.timesByL3([]);
        return true;
      };

      self.checkAnswer = function(event) {
        if (event.defaultPrevented) {
          return; // Do nothing if the event was already processed
        }
        // console.log("Key: "" + event.key + """);
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
          self.answerTime = getTimeNow() - self.startAnswer;
          self.totalAnswerTime += self.answerTime;
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
        setTimeout(function() {
          if (!($("#answer").is(":focus"))) {
            document.getElementById("answer").focus();
          }
        }, 500);
        // console.log("nextQuestion: position=" + self.position +
        //             ", num calculations=" + self.calculations.length);
        if (self.position >= self.calculations.length) {
          self.position = 0;
        }
        let numAnswers = self.numAnswers();
        numAnswers++;
        if (numAnswers > self.numQuestions()) {
          self.finishQuestions();
          return;
        }
        self.numAnswers(numAnswers);
        self.currentCalc = self.calculations[self.position++];
        // console.log("nextQuestion: calc=" + JSON.stringify(self.currentCalc, null, 2));
        self.factor1(self.currentCalc.times);
        self.factor2(self.currentCalc.by);
        self.symbol(self.currentCalc.op === "*" ? self.multiplySymbol : self.divideSymbol);
        self.userAnswer = 0;
        self.calcAnswer = parseFloat(self.currentCalc.answer);
        self.startAnswer = getTimeNow();
      };

      self.endQuestions = function() {
        console.log("endQuestions");
        $("#answer").off("blur");
        self.starting(false);
        self.stopTimer();
        self.startTime = 0;
        self.now(0);
        self.running(false);
        self.isComplete(false);
      };

      self.startQuestions = function() {
        console.log("startQuestions");
        self.running(true);
        self.correctAnswers = 0;
        self.numAnswers(0);
        self.position = 0;
        self.incorrectAnswers = [];
        self.startTime = getTimeNow();
        self.startAnswer = 0;
        self.answerTime = 0;
        self.totalAnswerTime = 0;
        self.avgAnswerTime(0);
        $("#answer").focus();
        self.startTimer();
        self.nextQuestion();
      };

      self.finishQuestions = function() {
        console.log("finishQuestions");
        $("#answer").off("blur");
        self.starting(false);
        self.stopTimer();
        const avg = self.totalAnswerTime / self.numQuestions();
        self.avgAnswerTime(Math.round((avg + 0.00001) * 100) / 100);
        self.resultTxt(self.resBegin + self.correctAnswers + self.resMiddle + self.numQuestions() + self.resEnd);
        self.incorrectResultsTxt("");
        let incorrectTxt = "";
        self.incorrectAnswers.forEach(function(inc) {
          // console.log("Incorrect: " + JSON.stringify(inc));
          incorrectTxt += "<p>" + inc.calc.times + " " + inc.symbol + " "
            + inc.calc.by + " = " + inc.calc.answer
            + " and not: " + inc.userAnswer + "</p>";
        });
        self.incorrectResultsTxt(incorrectTxt);
        self.isComplete(true);
        $("#answer").blur();
      };

      self.getCalculations = function() {
        self.starting(true);
        const timesBy = [].concat(self.timesByL1()).concat(self.timesByL2()).concat(self.timesByL3());
        self.op = self.operation();
        console.log("getCalculation, by=" + timesBy + ", op=" + self.op);
        const calcReq = {
          by: timesBy,
          op: self.op
        };
        console.log("Sending calc request: " + JSON.stringify(calcReq));
        console.log("App path is: " + self.path);
        fetch(self.path + "api/times", {
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
            self.startQuestions();
          });
      };

      self.handleActivated = function(info) {
        // Implement if needed
      };

      self.handleAttached = function(info) {
        // Implement if needed
      };

      self.handleBindingsApplied = function(info) {
        $("#answer").off("keydown").on("keydown", self.checkAnswer);
      };

      self.handleDetached = function(info) {
        // Implement if needed
      };
    }

    return new TimesViewModel();
  }
);
