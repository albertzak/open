var seed = 1

var MersenneTwister = function (seed) {
  if (seed == undefined) {
    seed = new Date().getTime();
  }
  this.N = 624;
  this.M = 397;
  this.MATRIX_A = 0x9908b0df;   /* constant vector a */
  this.UPPER_MASK = 0x80000000; /* most significant w-r bits */
  this.LOWER_MASK = 0x7fffffff; /* least significant r bits */

  this.mt = new Array(this.N); /* the array for the state vector */
  this.mti = this.N + 1; /* mti==N+1 means mt[N] is not initialized */

  this.init_genrand(seed);
}

MersenneTwister.prototype.init_genrand = function (s) {
  this.mt[0] = s >>> 0;
  for (this.mti = 1; this.mti < this.N; this.mti++) {
    var s = this.mt[this.mti - 1] ^ (this.mt[this.mti - 1] >>> 30);
    this.mt[this.mti] = (((((s & 0xffff0000) >>> 16) * 1812433253) << 16) + (s & 0x0000ffff) * 1812433253)
      + this.mti;
    this.mt[this.mti] >>>= 0;
  }
}

MersenneTwister.prototype.genrand_int32 = function () {
  var y;
  var mag01 = new Array(0x0, this.MATRIX_A);
  /* mag01[x] = x * MATRIX_A  for x=0,1 */

  if (this.mti >= this.N) { /* generate N words at one time */
    var kk;

    if (this.mti == this.N + 1)   /* if init_genrand() has not been called, */
      this.init_genrand(5489); /* a default initial seed is used */

    for (kk = 0; kk < this.N - this.M; kk++) {
      y = (this.mt[kk] & this.UPPER_MASK) | (this.mt[kk + 1] & this.LOWER_MASK);
      this.mt[kk] = this.mt[kk + this.M] ^ (y >>> 1) ^ mag01[y & 0x1];
    }
    for (; kk < this.N - 1; kk++) {
      y = (this.mt[kk] & this.UPPER_MASK) | (this.mt[kk + 1] & this.LOWER_MASK);
      this.mt[kk] = this.mt[kk + (this.M - this.N)] ^ (y >>> 1) ^ mag01[y & 0x1];
    }
    y = (this.mt[this.N - 1] & this.UPPER_MASK) | (this.mt[0] & this.LOWER_MASK);
    this.mt[this.N - 1] = this.mt[this.M - 1] ^ (y >>> 1) ^ mag01[y & 0x1];

    this.mti = 0;
  }

  y = this.mt[this.mti++];

  y ^= (y >>> 11);
  y ^= (y << 7) & 0x9d2c5680;
  y ^= (y << 15) & 0xefc60000;
  y ^= (y >>> 18);

  return y >>> 0;
}

MersenneTwister.prototype.random = function () {
  return this.genrand_int32() * (1.0 / 4294967295.0);
}

//
// Global functions used in tree.js
//
function resetRandom() {
  mt = new MersenneTwister(seed);
}

function tween(i, a, b, c, d) { //1, 62, 12, 3
  return (c - i) / c * b * (i / d)
}


function random(lower, upper) {
  return lower + mt.random() * (upper - lower);
}

function lerp(low, high, percent) {
  return low + (high - low) * percent;
}

function extendCanvasContext(ctx) {
  ctx.fillCircle = function (x, y, radius) {
    ctx.beginPath();
    ctx.arc(x, y, radius, 0, 2 * Math.PI, true);
    ctx.fill();
    ctx.closePath();
  }
}

window.resetRandom = resetRandom
window.tween = tween
window.random = random
window.lerp = lerp
window.extendCanvasContext = extendCanvasContext


var ctx, canvasWidth, canvasHeight;

function drawScene(canvas) {

  console.log('drawScene', canvas)

  ctx = canvas.getContext("2d");
  extendCanvasContext(ctx);

  canvasWidth = parseInt(canvas.getAttribute("width"));
  canvasHeight = parseInt(canvas.getAttribute("height"));

  drawSky();
  drawMountains();
  drawTree();
}


//---------------------------------------
//
//    sky
//

function drawSky() {
  ctx.save();

  var gradient = ctx.createLinearGradient(0, 0, 0, canvasHeight);
  gradient.addColorStop(0, "#b4e0fe");
  gradient.addColorStop(1, "#d3f8ff");

  ctx.fillStyle = gradient;

  ctx.fillRect(0, 0, canvasWidth, canvasHeight);

  ctx.restore();
}


//---------------------------------------
//
// mountains
//

function drawMountains() {
  resetRandom();

  drawMountain(130, "#8bb2bb");
  drawMountain(50, "#618087");
}

function drawMountain(offset, fillStyle) {
  var x = 0;
  var y = canvasHeight - offset;


  ctx.beginPath();
  ctx.moveTo(x, y);

  while (x >= 0 && x < canvasWidth) {
    x += random(2, 10);
    y += random(-4, 3);
    ctx.lineTo(x, y);
  }

  ctx.lineTo(canvasWidth, canvasHeight);
  ctx.lineTo(0, canvasHeight);
  ctx.closePath();

  ctx.fillStyle = fillStyle;
  ctx.fill();
}


//---------------------------------------
//
//    tree
//

function drawTree() {
  var blossomPoints = [];

  resetRandom();
  drawBranches(0, -Math.PI / 2, canvasWidth / 2, canvasHeight, 30, blossomPoints);

  resetRandom();
  drawBlossoms(blossomPoints);
}

function drawBranches(i, angle, x, y, width, blossomPoints) {
  ctx.save();

  var length = tween(i, 1, 62, 12, 3) * random(0.7, 1.3);
  if (i == 0) { length = 98; }

  ctx.translate(x, y);
  ctx.rotate(angle);
  ctx.fillStyle = "#000";
  ctx.fillRect(0, -width / 2, length, width);

  ctx.restore();

  var tipX = Math.floor(x + (length - width / 2) * Math.cos(angle));
  var tipY = Math.floor(y + (length - width / 2) * Math.sin(angle));

  if (i > 5) {
    blossomPoints.push([x, y, tipX, tipY]);
  }

  if (i < 6) {
    drawBranches(i + 1, angle + random(-0.15, -0.05) * Math.PI/*?*/, tipX, tipY, width * .65, blossomPoints);
    drawBranches(i + 1, angle + random(0.15, 0.05) * Math.PI/*?*/, tipX, tipY, width * .65, blossomPoints);
  }
  else if (i < 9) {
    drawBranches(i + 1, angle + random(0.15, 0.05) * Math.PI/*?*/, tipX, tipY, width * .6, blossomPoints);
  }
}

function drawBlossoms(blossomPoints) {
  var colors = ["#f5ceea", "#e8d9e4", "#f7c9f3", "#ebb4cc", "#cccccc"];//?
  ctx.globalAlpha = 0.60;

  for (var i = 0; i < blossomPoints.length; i++) {
    var p = blossomPoints[i];
    for (var j = 0; j < 16; j++) {
      var x = lerp(p[0], p[2], random(0, 1))// + random(-10,10);
      var y = lerp(p[1], p[3], random(0, 1))// + random(-10,10);


      ctx.fillStyle = colors[Math.floor(random(0, colors.length))];
      ctx.fillCircle(x, y, 5);
    }
  }

  ctx.globalAlpha = 1.0;
}

window.redrawScene = function (custom) {
  console.log(custom)
  drawScene(document.getElementById('canvas'))
}
