/*
 * Copyright (c) 2018 Simon Billingsley. All rights reserved.
 */
'use strict';

var path = require('path');

module.exports = function(grunt) {

  require('load-grunt-config')(grunt, {
  	configPath: path.join(process.cwd(), 'scripts/grunt/config')
  });

  grunt.loadNpmTasks("@oracle/grunt-oraclejet");

  grunt.registerTask("build", "Public task. Calls oraclejet-build to build the oraclejet application. Can be customized with additional build tasks.", function (buildType) {
    grunt.task.run([`oraclejet-build:${buildType}`]);
  });

  grunt.registerTask("release", "Public task. Calls oraclejet-build to build the oraclejet application. Can be customized with additional build tasks.", function (buildType) {
    grunt.task.run([`oraclejet-build:release`]);
  });

  grunt.registerTask("serve", "Public task. Calls oraclejet-serve to serve the oraclejet application. Can be customized with additional serve tasks.", function (buildType) {
    grunt.task.run([`oraclejet-serve:${buildType}`]);
  }); 
};

