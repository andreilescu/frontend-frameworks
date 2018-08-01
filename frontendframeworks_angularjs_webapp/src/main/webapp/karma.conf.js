// Karma configuration
// Generated on Sat May 02 2015 17:10:51 GMT+0200 (Mitteleuropäische Sommerzeit)

module.exports = function (config) {
    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: '',


        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine', 'browserify'],


        plugins: [
            'karma-firefox-launcher',
            'karma-ie-launcher',
            'karma-jasmine',
            'karma-browserify',
            'karma-coverage',
            'karma-story-reporter',
            'karma-ng-html2js-preprocessor'
        ],

        // list of files / patterns to load in the browser
        files: [
            'node_modules/angular-mocks/angular-mocks.js',
            'app/js/app.js',
            'app/js/**/*.js',
            'app/templates/**/*.html',
            'test/unit/**/*.js'
        ],


        // list of files to exclude
        exclude: [],


        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {
            'app/templates/**/*.html': 'ng-html2js',
            'app/js/services/**/*.js': ['coverage'],
            'app/js/directives/**/*.js': ['coverage'],
        },

        ngHtml2JsPreprocessor: {
            stripPrefix: 'app/',
            moduleName: 'ng'
        },

        coverageReporter: {
            type: 'html',
            dir: 'coverage/'
        },


        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress', 'coverage', 'story'],


        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,


        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['Firefox', 'IE'],


        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: true
    });
};
