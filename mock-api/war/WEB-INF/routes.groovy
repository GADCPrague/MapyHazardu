
get "/", forward: "/WEB-INF/pages/index.gtpl"
get "/datetime", forward: "/datetime.groovy"

get "/datastore", forward: "/dataStoreGroovlet.groovy"

post "/api", forward: "/post.groovy"

get "/api", forward: "/get.groovy"

get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"
