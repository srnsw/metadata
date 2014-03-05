var vows = require('vows'),
    assert = require('assert'),
    fs = require('fs'),
    schema = require('../schema.json'),
    walk = require('walk'),
    path = require('path');

var Validator = require('jsonschema').Validator;

vows.describe('Metadata').addBatch({
    'Schema': {
        topic: new(Validator),
        'valid': function (validator) {
            var walker  = walk.walk('../samples', { followLinks: false });
            
            walker.on('file', function(root, stat, next) {
                 var instance = require(path.join(root, stat.name));
                 assert.isTrue(validator.validate(instance, schema).errors.length == 0);
                next();
            });
        }
    }
}).export(module);