define(["../PersistenceStore","../impl/storageUtils","pouchdb","./logger"],function(a,b,c,d){"use strict";var e=function(b){a.call(this,b)};e.prototype=new a,e.prototype.Init=function(a){this._version=a&&a.version||"0";var b=this._name+this._version,e=a?a.adapter:null,f=this._extractDBOptions(a);if(e)try{e.plugin&&c.plugin(e.plugin),f=f||{},f.adapter=e.name,this._db=new c(b,f)}catch(a){return d.log("Error creating PouchDB instance with adapter "+e+": ",a.message),d.log("Please make sure the needed plugin and adapter are installed."),Promise.reject(a)}else this._db=f?new c(b,f):new c(b);return this._index=a&&a.index?a.index:null,this._createIndex()},e.prototype._extractDBOptions=function(a){var b=null;if(a){var c=this;Object.keys(a).forEach(function(d){c._isPersistenceStoreKey(d)||(b||(b={}),b[d]=a[d])})}return b},e.prototype._isPersistenceStoreKey=function(a){return"version"===a||"adapter"===a||"index"===a},e.prototype._createIndex=function(){if(this._index&&this._db.createIndex){var a=this,b=a._name+a._index.toString().replace(",","").replace(".",""),c={index:{fields:a._index,name:b}};return a._db.createIndex(c)}return Promise.resolve()},e.prototype.upsert=function(a,b,c,e){d.log("Offline Persistence Toolkit pouchDBPersistenceStore: upsert() for key: "+a);var g=this,h=a.toString(),i=[];return this._prepareUpsert(c,i),g._db.get(h).then(function(a){return f(e,a)?a:Promise.reject({status:409})}).catch(function(a){return 404===a.status&&"missing"===a.message?Promise.resolve():Promise.reject(a)}).then(function(a){return g._put(h,b,c,e,i,a)}).then(function(){return Promise.resolve()})},e.prototype._put=function(a,b,c,d,e,g){var h={_id:a,metadata:b,value:c};g&&(h._rev=g._rev);var i=this;return i._db.put(h).then(function(a){return Promise.resolve(a)}).catch(function(b){return 409===b.status?i._db.get(a).then(function(a){return d?f(d,a)?Promise.resolve(a):Promise.reject({status:409}):Promise.resolve(a)}):Promise.reject(b)}).then(function(a){return i._addAttachments(a,e)}).then(function(){return Promise.resolve()})};var f=function(a,b){if(a){return b.metadata.versionIdentifier===a}return!0};return e.prototype._addAttachments=function(a,b){if(b&&b.length){var c=b.map(function(b){var c;return c=b.value instanceof Blob?b.value:new Blob([b.value]),this._db.putAttachment(a.id,b.path,a.rev,c,"binary")},this);return Promise.all(c)}return Promise.resolve()},e.prototype.upsertAll=function(a){if(d.log("Offline Persistence Toolkit pouchDBPersistenceStore: upsertAll()"),a&&a.length){var b=a.map(function(a){return this.upsert(a.key,a.metadata,a.value,a.expectedVersionIdentifier)},this);return Promise.all(b)}return Promise.resolve()},e.prototype.find=function(a){d.log("Offline Persistence Toolkit pouchDBPersistenceStore: find() for expression: "+JSON.stringify(a));var c=this;if(a=a||{},c._db.find){var e=c._prepareFind(a);return c._db.find(e).then(function(a){if(a&&a.docs&&a.docs.length){var b=a.docs.map(c._findResultCallback(e.fields),c);return Promise.all(b)}return[]}).catch(function(a){if(404===a.status&&"missing"===a.message)return[];throw a})}return c._db.allDocs({include_docs:!0}).then(function(d){if(d&&d.rows&&d.rows.length){var e=d.rows.filter(function(d){var e=d.doc;return c._fixKey(e),!!b.satisfy(a.selector,e)});if(e.length){var f=e.map(function(d){return c._fixBinaryValue(d.doc).then(function(c){return a.fields?b.assembleObject(c,a.fields):c.value})});return Promise.all(f)}return[]}return[]}).catch(function(a){return d.log("error retrieving all documents from pouch db, returns empty list as find operation.",a),[]})},e.prototype._findResultCallback=function(a){return function(b){return this._fixValue(b).then(function(b){return a?b:b.value})}},e.prototype._fixValue=function(a){return this._fixKey(a),this._fixBinaryValue(a)},e.prototype._fixBinaryValue=function(a){var b=a._id||a.id||a.key,c=a._attachments;if(c){var d=this,e=Object.keys(c)[0];return d._db.getAttachment(b,e).then(function(b){for(var c=e.split("."),d=a.value,f=0;f<c.length-1;f++)d=d[c[f]];return d[c[c.length-1]]=b,a})}return Promise.resolve(a)},e.prototype._fixKey=function(a){var b=a._id||a.id||a.key;b&&(a.key=b)},e.prototype.findByKey=function(a){d.log("Offline Persistence Toolkit pouchDBPersistenceStore: findByKey() for key: "+a);var b=this,c=a.toString();return b._db.get(c).then(function(a){return a.value}).catch(function(a){return 404===a.status&&"missing"===a.message?Promise.resolve():Promise.reject(a)})},e.prototype.removeByKey=function(a){d.log("Offline Persistence Toolkit pouchDBPersistenceStore: removeByKey() for key: "+a);var b=this,c=a.toString();return b._db.get(c).then(function(a){return b._db.remove(a)}).then(function(){return!0}).catch(function(a){return 404===a.status&&"missing"===a.message?Promise.resolve(!1):Promise.reject(a)})},e.prototype.delete=function(a){d.log("Offline Persistence Toolkit pouchDBPersistenceStore: delete() for expression: "+JSON.stringify(a));var b=this;if(a){var e=a;return e.fields=["_id","_rev"],b.find(e).then(function(a){if(a&&a.length){var c=a.map(function(a){return this._db.remove(a._id,a._rev)},b);return Promise.all(c)}return Promise.resolve()}).then(function(){return Promise.resolve()})}return b._db.destroy().then(function(){var a=b._name+b._version;return b._db=new c(a),b._createIndex()})},e.prototype.keys=function(){return d.log("Offline Persistence Toolkit pouchDBPersistenceStore: keys()"),this._db.allDocs().then(function(a){var b=a.rows,c=[];return b&&b.length&&(c=b.map(function(a){return a.id})),c})},e.prototype._prepareFind=function(a){var b={},c=a.selector;b.selector=c||{_id:{$gt:null}};var d=a.fields;if(d&&d.length){var e=d.map(function(a){return"key"===a?"_id":a});b.fields=e}return b},e.prototype._prepareUpsert=function(a,b){this._inspectValue("",a,b)},e.prototype._inspectValue=function(a,b,c){for(var d in b)if(b.hasOwnProperty(d)){var e=b[d];if(e&&"object"==typeof e)if(e instanceof Blob||e instanceof ArrayBuffer){var f=a;f.length>0&&(f+=".");var g={path:f+d,value:e};c.push(g),b.key=null}else if(void 0===e.length){var h=a;a.length>0&&(a+="."),a+=d,this._inspectValue(a,e,c),a=h}}},e});