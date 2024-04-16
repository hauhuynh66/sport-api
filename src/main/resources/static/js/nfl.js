function teamInfo(code) {
    window.location = '/admin/nfl/team/' + code;
};

function matchInfo(id) {
    window.location = '/admin/nfl/schedule/info?id=' + id;
};

$("#season-selector").on("change", (e)=> {
    window.location = '/admin/nfl/schedule?season=' + $("#season-selector option:selected").text();
})