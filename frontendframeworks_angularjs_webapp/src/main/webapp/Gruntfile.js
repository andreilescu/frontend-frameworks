module.exports = function(grunt) {

    var appName = 'frontendframeworks';
    var targetDir = 'dist';

    grunt.initConfig({

        pkg: grunt.file.readJSON('package.json'),

        copy: {
            main: {
                files: [
                    {
                        expand: true,
                        cwd: 'app/',
                        src: ['css/**', 'partials/**', 'templates/**', 'index.html'],
                        dest: targetDir
                    },
                    {
                        expand: true,
                        cwd: 'node_modules/bootstrap/dist/css/',
                        src: ['bootstrap.min.css'],
                        dest: targetDir + '/css'
                    },
                    {
                        expand: true,
                        cwd: 'node_modules/jquery/dist/',
                        src: ['jquery.min.js'],
                        dest: targetDir + '/js'
                    },
                    {
                        expand: true,
                        cwd: 'node_modules/popper.js/dist/',
                        src: ['popper.min.js'],
                        dest: targetDir + '/js'
                    },
                    {
                        expand: true,
                        cwd: 'node_modules/bootstrap/dist/js/',
                        src: ['bootstrap.min.js'],
                        dest: targetDir + '/js'
                    }
                ]
            }
        },

        jshint: {
            js: {
                src: ['app/js/app.js', 'app/js/**/*.js', 'test/**']
            },
            options: {
                reporter: require('jshint-stylish')
            }
        },

        ngAnnotate: {
            js: {
                files: [
                    {
                        src: ['app/js/app.js', 'app/js/**/*.js'],
                        dest: 'dist/js/' + appName + '.js'
                    },
                    {
                        src: [
                            'node_modules/angular/angular.min.js',
                            'node_modules/angular-route/angular-route.min.js'
                        ],
                        dest: 'dist/js/' + 'vendors.min.js'
                    }]
            }
        },

        compass: {
            dist: {
                options: {
                    basePath: 'app',
                    sassDir: 'sass',
                    cssDir: '../dist/css',
                    environment: 'production'
                }
            }
        },

        inline_angular_templates: {
            dist: {
                options: {
                    base: 'app',
                    selector: 'body',
                    method: 'append',
                    unescape: {}
                },
                files: {
                    'dist/index.html': ['app/partials/**/*.html']
                }
            }
        },

        uglify: {
            js: {
                options: {
                    banner: '/* <%= pkg.name %> - v<%= pkg.version %>\n<%= pkg.license %> */\n'
                },
                src: [targetDir + '/js/' + appName + '.js'],
                dest: targetDir + '/js/' + appName + '.min.js'
            }
        },

        clean: [targetDir]
    });

    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-compass');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-ng-annotate');
    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-inline-angular-templates');
    grunt.registerTask('generate-sources', ['compass', 'jshint', 'copy', 'ngAnnotate', 'uglify', 'inline_angular_templates']);
    grunt.registerTask('test', ['karma:cdi']);
    grunt.registerTask('test-browser', ['karma:browser', 'karma:cdi']);
    grunt.registerTask('package', ['generate-sources']);
    grunt.registerTask('default', ['clean', 'package']);
};