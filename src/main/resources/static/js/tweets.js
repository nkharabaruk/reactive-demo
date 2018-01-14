document.addEventListener("DOMContentLoaded", function (event) {
    getTweets(baseURL);
});

var tweetList = document.getElementById("tweet-list");
var baseURL = "http://localhost:8080/tweets";
var tagList = [];
var xhr;

function sendRequest(param) {
    xhr.abort();
    tweetList.innerHTML = '';
    toggleParam(param);
    getTweets(getURL());
}

function toggleParam(param) {
    if (tagList.indexOf(param) > -1) {
        tagList.splice(tagList.indexOf(param), 1);
    } else {
        tagList.push(param);
    }
}

function getURL() {
    if (tagList.length == 0) {
        url = baseURL;
    } else {
        url = baseURL + '?tags=' + tagList;
    }
    return url;
}

function getTweets(url) {
    xhr = new XMLHttpRequest();
    var size = 0;
    xhr.open("GET", url, true);
    xhr.onprogress = function () {
        var arr = xhr.responseText.split("\n");
        var newTweets = arr.slice(size, arr.length - 1);
        for (var i = 0; i < newTweets.length; i++) {
            var tweet = JSON.parse(newTweets[i]);
            tweetList.innerHTML = tweetList.innerHTML.concat("\n" +
                '<div>' +
                '<h3>' + tweet.author + '</h3>' +
                '<h4>' + tweet.text + '</h4>' +
                '<h5>' + tweet.date + '</h5>' +
                '<h6>' + tweet.hashTags + '</h6>' +
                '</div>');
        }
        console.log("PROGRESS:", newTweets);
        size += newTweets.length;
    };
    xhr.send();
}