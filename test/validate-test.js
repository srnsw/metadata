var vows = require('vows'),
assert = require('assert'),
fs = require('fs'),
schema = require('../schema.json'),
walk = require('walk'),
path = require('path');

var Validator = require('jsonschema').Validator;

vows.describe('Metadata').addBatch({
    'Samples': {
        topic: new(Validator),
        'valid': function (validator) {
            assert.notEqual(schema, null)
            
            var files = fs.readdirSync(path.join(process.cwd(), 'samples'));

            for (var file in files) {
               var validation = validator.validate(require(path.join(process.cwd(), 'samples', files[file])), schema);
               assert.isTrue(validation.errors.length==0)
            }

        }
    }
}).export(module);