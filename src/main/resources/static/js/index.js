tinymce.init({
    selector: "#commentAns",
    content_style: "p{margin:0}",
});
tinymce.init({
                    selector: "#post-question__answer",
                    menubar: false,
                    plugins: "image imagetools",
                    toolbar:
                        "undo redo | styleselect | forecolor | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | link image",
                    image_title: true,
                    automatic_uploads: true,
                    height: "375",
                    file_picker_callback: function (cb, value, meta) {
                        var input = document.createElement("input");
                        input.setAttribute("type", "file");
                        input.setAttribute("accept", "image/*");
                        input.onchange = function () {
                            var file = this.files[0];
                            var reader = new FileReader();
                            reader.onload = function () {
                                var id = "blobid" + new Date().getTime();
                                var blobCache = tinymce.activeEditor.editorUpload.blobCache;
                                var base64 = reader.result.split(",")[1];
                var blobInfo = blobCache.create(id, file, base64);
                blobCache.add(blobInfo);
                cb(blobInfo.blobUri(), { title: file.name });
            };
            reader.readAsDataURL(file);
        };
        input.click();
    },
    content_css: "./css/tinymce.css",
});
moment.locale("vi");
setInterval(function () {
    var parse = moment().format("LL,LTS");
    $(".time").text(parse);
}, 1000);

