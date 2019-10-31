define({
    load: function (name, parentRequire, onload, config) {
        if (typeof vm === 'undefined') {
            onload({});
        } else {
            vm.loadClass(name, function(MainClass) {
                MainClass.invoke("main");
                onload(MainClass);
            });
        }
    }
});

