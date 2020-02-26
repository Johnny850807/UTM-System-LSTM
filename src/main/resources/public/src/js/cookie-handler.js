function saveTokenToCookie(tokenName, token){
    var days = 7;//cookie儲存7天
    var date = new Date();
    date.setTime(date.getTime() + days*24*60*60*1000);
    var expires = "expires="+date.toGMTString();
    var utmTokenName = tokenName;
    var utmTokenValue = token;
    document.cookie = utmTokenName + "=" + utmTokenValue + "; " + expires;
}

function getToken(tokenName){
    var name = tokenName + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
}