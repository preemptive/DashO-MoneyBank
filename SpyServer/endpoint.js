#!/usr/bin/env node
/*
 *
 * Modified from PANodeEndpoint to support https.
 * 
 * https://github.com/preemptive/PANodeEndpoint
 *
 * This creates a simple endpoint that outputs all requests to a file.
 * Usage: node endpoint.js [port [filename]]
 * The default port is 8080
 * The default output is stdout
 */
var http = require("https");
var fs = require('fs');
var count=1;
var body = 'Hello World!';
var len = body.length;
var ret = 200

var port = 8080
if (process.argv.length > 2) {
    port = process.argv[2]
}
var file = null;
if (process.argv.length > 3) {
    file = process.argv[3]
}

http.createServer({ key: fs.readFileSync("server_dev.key"), cert: fs.readFileSync("server_dev.crt") }, function(request,response){
    //console.log("Message Received: "+(count++))
    console.log(request.headers);
    if (file != null) {
        request.pipe(fs.createWriteStream(file, {'flags': 'a'}));
    } else {
        request.pipe(process.stdout);
    }
    //console.log(request);
    response.writeHead(ret, {
        'Content-Length': len,
        'Content-Type': 'text/plain' });
    response.write(body);
    response.end();
}).listen(port);

console.log("Server Running on "+port);

