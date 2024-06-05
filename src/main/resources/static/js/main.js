$("#type-selector").on("change", () => {
    console.log("changed");
    window.location = '/admin/enc/list?type=' + $("#type-selector option:selected").val();
});

function encDetail(id) {
    window.location = '/admin/enc/' + id;
};