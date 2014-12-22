
# MobFox Plugin Pro #

Present MobFox Ads in Mobile App/Games natively from JavaScript. 

Highlights:
- [x] Easy-to-use APIs. Display Ad with single line of Js code.
- [x] Support Banner, Interstitial Ad, and Video Ad.
- [x] One plugin supports both Android and iOS platform.
- [x] Multiple banner size, also support custom size.
- [x] Fixed and overlapped mode.
- [x] Auto fit on orientation change.

Compatible with:

* [x] Cordova CLI, v3.5+
* [x] Intel XDK and Crosswalk, r1095+
* [x] IBM Worklight, v6.2+
* [x] Google Mobile Chrome App
* [x] Adobe PhoneGap Build, since 2014/12/9

## How to use? ##

* If use with Cordova CLI:
```
cordova plugin add com.rjfun.cordova.mobfox
```

* If use with PhoneGap Buid, just configure in config.xml:
```javascript
<gap:plugin name="com.rjfun.cordova.mobfox" source="plugins.cordova.io"/>
```

* If use with Intel XDK:
Project -> CORDOVA 3.X HYBRID MOBILE APP SETTINGS -> PLUGINS AND PERMISSIONS -> Third-Party Plugins ->
Add a Third-Party Plugin -> Get Plugin from the Web, input:
```
Name: MobFoxPluginPro
Plugin ID: com.rjfun.cordova.mobfox
[x] Plugin is located in the Apache Cordova Plugins Registry
```

## Quick start with cordova CLI ##

Add the plugin to your cordova project with [Cordova CLI](https://cordova.apache.org/docs/en/edge/guide_cli_index.md.html#The%20Command-Line%20Interface):
```bash
cordova create <project_folder> com.<company_name>.<app_name> <AppName>
cd <project_folder>
cordova platform add android
cordova platform add ios

cordova plugin add com.rjfun.cordova.mobfox

// copy the demo html file to www
rm -r www/*; cp plugins/com.rjfun.cordova.mobfox/test/index.html www/

// connect device or run in emulator
cordova prepare; cordova run android; cordova run ios;

// or import into Xcode / eclipse
```

## Quick Start Example Code ##

Step 1: Prepare your Mobfox publisher Id for your app, create it in [MobFox website](http://www.mobfox.com/)

```javascript
var ad_units = {
	ios : "publisher_id_for_ios_xxx",
	android : "publisher_id_for_android_xxx"
};

// select the right Ad Id according to platform
var publisherId = ( /(android)/i.test(navigator.userAgent) ) ? ad_units.android : ad_units.ios;
```

Step 2: Create a banner with single line of javascript

```javascript
// it will display smart banner at top center, using the default options
if(MobFox) MobFox.createBanner( publisherId );
```

Or, show the banner Ad in some other way:

```javascript
// or, show a default banner at bottom
if(MobFox) MobFox.createBanner( {
	adId: publisherId, 
	position:MobFox.AD_POSITION.BOTTOM_CENTER, 
	autoShow:true} );
```

Step 3: Prepare an interstitial, and show it when needed

```javascript
// preppare and load ad resource in background, e.g. at begining of game level
if(MobFox) MobFox.prepareInterstitial( {adId:publisherId, autoShow:false} );

// show the interstitial later, e.g. at end of game level
if(MobFox) MobFox.showInterstitial();
```

Step 4: Prepare an video Ad, and show it when needed

```javascript
// preppare and load ad resource in background, e.g. at begining of game level
if(MobFox) MobFox.prepareInterstitial( {adId:publisherId, enableVideo:true, autoShow:false} );

// show the interstitial later, e.g. at end of game level
if(MobFox) MobFox.showInterstitial();
```


## Full Example Code ##
Check the [test/index.html] (https://github.com/floatinghotpot/cordova-mobfox-pro/blob/master/test/index.html).

## API Overview ##

### Methods ###
```javascript```
MobFox.setOptions(options);

MobFox.createBanner(adId/options, success, fail);
MobFox.removeBanner();
MobFox.showBanner(position);
MobFox.showBannerAtXY(x, y);
MobFox.hideBanner();

MobFox.prepareInterstitial(adId/options, success, fail);
MobFox.showInterstitial();
```

### Events ###
> **Syntax**: document.addEventListener(event_name, callback);

```javascript
// for both banner and interstitial
'onAdFailLoad'
'onAdLoaded'
'onAdPresent'
'onAdLeaveapp'
'onAdDismiss'

## Methods ##

### MobFox.setOptions(options) ###

> **Purpose**: Set default values for other methods. All the option items are optional, will use default value if missing.

**Params**:
- **options**, *json object*, mapping key to value.

key/value for param **options**:
- **license**, *string*, set the license key, to remove the 2% Ad traffic sharing
- **adId**, *string*, set the MobFox publisher Id
- **adSize**, *string*, banner Ad size, Default:'SMART_BANNER'. it can be: (see the screenshots for effects)
```javascript
'SMART_BANNER',
'BANNER', 
'FULL_BANNER', 
'MEDIUM_RECTANGLE', 
'LEADERBOARD', 
'SKYSCRAPER', 
'CUSTOM', // custom banner size with given width and height, see param 'width' and 'height'
```
- **width**, *integer*, banner width, valid when set *adSize*:'CUSTOM'. Default: 0
- **height**, *integer*, banner height, valid when set *adSize*:'CUSTOM'. Default: 0
- **overlap**, *boolean@, allow banner overlap webview, or else will push webview up or down to avoid overlap. Default:false
- **position**, *integer*, position of banner Ad, Default:TOP_CENTER. Value can be one of: 
```javascript
MobFox.AD_POSITION.NO_CHANGE  	= 0,
MobFox.AD_POSITION.TOP_LEFT 		= 1,
MobFox.AD_POSITION.TOP_CENTER 	= 2,
MobFox.AD_POSITION.TOP_RIGHT 	= 3,
MobFox.AD_POSITION.LEFT 			= 4,
MobFox.AD_POSITION.CENTER 		= 5,
MobFox.AD_POSITION.RIGHT 		= 6,
MobFox.AD_POSITION.BOTTOM_LEFT 	= 7,
MobFox.AD_POSITION.BOTTOM_RIGHT 	= 8,
MobFox.AD_POSITION.BOTTOM_RIGHT 	= 9,
MobFox.AD_POSITION.POS_XY 		= 10, // use the given X and Y, see params 'x' and 'y'
```
- **x**, *integer*, x in pixels. Valid when *overlap*:true and *position*:MobFox.AD_POSITION.POS_XY. Default: 0
- **y**, *integer*, y in pixels. Valid when *overlap*:true and *position*:MobFox.AD_POSITION.POS_XY. Default: 0
- **isTesting**, *boolean*, set to true, to receiving test ad for testing purpose
- **autoShow**, *boolean*, auto show interstitial ad when loaded, set to false if hope to control the show timing with prepareInterstitial/showInterstitial
- **orientationRenew**, *boolean*, re-create the banner on web view orientation change (not screen orientation), or else just move the banner. Default:true.
- **enableVideo**, *boolean*, enable video in interstitial ad.

Example Code:
```javascript
var defaultOptions = {
    license: 'username@gmail.com/xxxxxxxxxxxxxxx',
	adId: 'your_mobfox_publisher_id_here',
	adSize: 'SMART_BANNER',
	width: 360, // valid when set adSize 'CUSTOM'
	height: 90, // valid when set adSize 'CUSTOM'
	position: MobFox.AD_POSITION.BOTTOM_CENTER,
	x: 0,		// valid when set position to POS_XY
	y: 0,		// valid when set position to POS_XY
	isTesting: true,
	autoShow: true
};
MobFox.setOptions( defaultOptions );
```

### MobFox.createBanner(adId/options, success, fail) ###

> **Purpose**: create a banner Ad.

**Param**
- **adId**, *string*, the Ad unit Id for banner.
- **options**, *json object*, see the keys in **MobFox.setOptions**
- **success**, *function*, callback when success, can be null or missing.
- **fail**, *function*, callback when fail, can be null or missing.

Extra key/value for param **options**
- **adId**, *string*, Ad unit Id for this banner.
- **success**, *function*, callback when success.
- **error**, *function*, call back when fail.

Example Code:
```javascript
MobFox.createBanner( "your_publisher_id_here" );

MobFox.createBanner({
	adId: "your_publisher_id_here",
	position: MobFox.AD_POSITION.BOTTOM_CENTER,
	autoShow: true,
	success: function(){
	},
	error: function(){
		alert('failed to create banner');
	}
});
```
## MobFox.showBanner(position) ##

> **Purpose**: show banner at given position. It can also be used to move banner to given position.  It's not needed to removeBannr and create a new one.

Params:
- **position**, *integer*, see description in **MobFox.setOptions()**

## MobFox.showBannerAtXY(x, y) ##

> **Purpose**: show banner at given position with (x,y). 

Params:
- **x**, *integer*, in pixels. Offset from screen left.
- **y**, *integer*, in pixels. Offset from screen top.

### MobFox.hideBanner() ###

> **Purpose**: hide the banner, remove it from screen, but can show it later. 

### MobFox.removeBanner() ###

> **Purpose**: destroy the banner, remove it from screen. 

You can create another banner if different size, need remove the old one.

## MobFox.prepareInterstitial(adId/options, success, fail) ##

> **Purpose**: prepare an interstitial Ad for showing.

Params:
- **adId**, *string*, Ad unit Id for the full screen Ad.
- **options**, *string*, see **MobFox.setOptions()*
- **success**, *function*, callback when success, can be null or missing.
- **fail**, *function*, callback when fail, can be null or missing.

Extra key/value for param **options**
- **adId**, *string*, publisher Id for this interstitial.
- **success**, *function*, callback when success.
- **error**, *function*, call back when fail.

> Note: it will take some time to get Ad resource before it can be showed. You may buffer the Ad by calling **prepareInterstitial**, and show it later.

## MobFox.showInterstitial() ##

> **Purpose**: show interstitial Ad when it's ready.

Example Code:
```javascript
// prepare and aut show
MobFox.prepareInterstitial({
	adId: "your_publisher_id_here",
	autoShow: true
});

// prepare at beginning of a game level
MobFox.prepareInterstitial({
	adId: "your_publisher_id_here",
	autoShow: false
});
// check and show it at end of a game level
MobFox.showInterstitial();
```

## Events ##

All following events will come with a data param, with properties:
* data.adNetwork, the Ad network name, like 'AdMob', 'Flurry', 'iAd', 'MobFox', etc.
* data.adType, 'banner' or 'interstitial'
* data.adEvent, the event name

'onAdFailLoad'
> Triggered when failed to receive Ad. 
```javascript
document.addEventListener('onAdFailLoad',function(data){
	console.log( data.error + ',' + data.reason );
	if(data.adType == 'banner') AdMob.hideBanner();
	else if(data.adType == 'interstitial') interstitialIsReady = false;
});
```

'onAdLoaded'
> Triggered when Ad received.
```javascript
document.addEventListener('onAdLoaded',function(data){
	MobFox.showBanner();
});
MobFox.createBanner({
	adId: admobid.banner,
	autoShow: false
});
```

'onAdPresent'
> Triggered when Ad will be showed on screen.

'onAdLeaveApp'
> Triggered when user click the Ad, and will jump out of your App.

'onAdDismiss'
> Triggered when dismiss the Ad and back to your App.

