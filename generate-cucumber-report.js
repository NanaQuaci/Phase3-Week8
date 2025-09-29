// generate-cucumber-report.js
const reporter = require('cucumber-html-reporter');

const options = {
  theme: 'bootstrap',
  jsonFile: 'cucumber-report/cucumber.json',
  output: 'cucumber-report/index.html',
  reportSuiteAsScenarios: true,
  launchReport: false,
  metadata: {
    "App Version": "1.0.0",
    "Test Environment": "GitHub Actions",
    "Browser": "Chrome",
    "Platform": "Ubuntu",
  },
};

reporter.generate(options);
