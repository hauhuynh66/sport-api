function teamInfo(code) {
    window.location = '/admin/nfl/team/' + code;
};

function matchInfo(id) {
    window.location = '/admin/nfl/matches/info?id=' + id;
};

$("#season-selector").on("change", () => {
    window.location = '/admin/nfl/matches?season=' + $("#season-selector option:selected").text();
})