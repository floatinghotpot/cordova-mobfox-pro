
var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

var mobfoxExport = {};

mobfoxExport.AD_SIZE = {
  SMART_BANNER: 'SMART_BANNER',
  BANNER: 'BANNER',
  MEDIUM_RECTANGLE: 'MEDIUM_RECTANGLE',
  FULL_BANNER: 'FULL_BANNER',
  LEADERBOARD: 'LEADERBOARD',
  SKYSCRAPER: 'SKYSCRAPER'
};

mobfoxExport.AD_POSITION = {
  NO_CHANGE: 0,
  TOP_LEFT: 1,
  TOP_CENTER: 2,
  TOP_RIGHT: 3,
  LEFT: 4,
  CENTER: 5,
  RIGHT: 6,
  BOTTOM_LEFT: 7,
  BOTTOM_CENTER: 8,
  BOTTOM_RIGHT: 9,
  POS_XY: 10
};

/*
 * set options:
 *  {
 *    adSize: string,	// banner type size
 *    width: integer,	// banner width, if set adSize to 'CUSTOM'
 *    height: integer,	// banner height, if set adSize to 'CUSTOM'
 *    position: integer, // default position
 *    x: integer,	// default X of banner
 *    y: integer,	// default Y of banner
 *    isTesting: boolean,	// if set to true, to receive test ads
 *    enableVideo: boolean,	// if set to true, enable video for interstitial
 *    autoShow: boolean,	// if set to true, no need call showBanner or showInterstitial
 *   }
 */
mobfoxExport.setOptions = function(options, successCallback, failureCallback) {
	  if(typeof options === 'object') {
		  cordova.exec( successCallback, failureCallback, 'MobFox', 'setOptions', [options] );
	  } else {
		  if(typeof failureCallback === 'function') {
			  failureCallback('options should be specified.');
		  }
	  }
	};

mobfoxExport.createBanner = function(args, successCallback, failureCallback) {
	var options = {};
	if(typeof args === 'object') {
		for(var k in args) {
			if(k === 'success') { if(args[k] === 'function') successCallback = args[k]; }
			else if(k === 'error') { if(args[k] === 'function') failureCallback = args[k]; }
			else {
				options[k] = args[k];
			}
		}
	} else if(typeof args === 'string') {
		options = { adId: args };
	}
	cordova.exec( successCallback, failureCallback, 'MobFox', 'createBanner', [ options ] );
};

mobfoxExport.removeBanner = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'MobFox', 'removeBanner', [] );
};

mobfoxExport.hideBanner = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'MobFox', 'hideBanner', [] );
};

mobfoxExport.showBanner = function(position, successCallback, failureCallback) {
	if(typeof position === 'undefined') position = 0;
	cordova.exec( successCallback, failureCallback, 'MobFox', 'showBanner', [ position ] );
};

mobfoxExport.showBannerAtXY = function(x, y, successCallback, failureCallback) {
	if(typeof x === 'undefined') x = 0;
	if(typeof y === 'undefined') y = 0;
	cordova.exec( successCallback, failureCallback, 'MobFox', 'showBannerAtXY', [{x:x, y:y}] );
};

mobfoxExport.prepareInterstitial = function(args, successCallback, failureCallback) {
	var options = {};
	if(typeof args === 'object') {
		for(var k in args) {
			if(k === 'success') { if(args[k] === 'function') successCallback = args[k]; }
			else if(k === 'error') { if(args[k] === 'function') failureCallback = args[k]; }
			else {
				options[k] = args[k];
			}
		}
	} else if(typeof args === 'string') {
		options = { adId: args };
	}
	cordova.exec( successCallback, failureCallback, 'MobFox', 'prepareInterstitial', [ args ] );
};

mobfoxExport.showInterstitial = function(successCallback, failureCallback) {
	cordova.exec( successCallback, failureCallback, 'MobFox', 'showInterstitial', [] );
};

module.exports = mobfoxExport;

