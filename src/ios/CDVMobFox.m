
#import "CDVMobFox.h"

@interface CDVMobFox()

@property(nonatomic, retain) MobFoxAds* _MobFoxApi;

@end

@implementation CDVMobFox
@synthesize _MobFoxApi;

- (UIView*) getView {
    return self.webView;
}

- (UIViewController*) getViewController {
    return self.viewController;
}

- (void) onEvent:(NSString *)eventType withData:(NSString *)jsonString {
    NSString * js = nil;
    if(jsonString != nil) {
        js = [NSString stringWithFormat:@"cordova.fireDocumentEvent('%@', %@ );", eventType, jsonString];
    } else {
        js = [NSString stringWithFormat:@"cordova.fireDocumentEvent('%@');", eventType];
    }
    [self.commandDelegate evalJs:js];
}

- (void) evalJs:(NSString*) js
{
    [self.commandDelegate evalJs:js];
}

- (CDVPlugin *)initWithWebView:(UIWebView *)theWebView {
	self = (CDVMobFox *)[super initWithWebView:theWebView];
	if (self) {
        _MobFoxApi = [[MobFoxAds alloc] init:self];
	}
    
	return self;
}

- (void) setOptions:(CDVInvokedUrlCommand *)command {
    NSLog(@"setOptions");
    
    if([command.arguments count] > 0) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        [_MobFoxApi setOptions:options];
    }
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}


- (void)createBanner:(CDVInvokedUrlCommand *)command {
    NSLog(@"createBanner");

    if([command.arguments count] > 0) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        if([options count] > 1) {
            [_MobFoxApi setOptions:options];
        }
        
        NSString* adId = [options objectForKey:@"adId"];
        dispatch_async(dispatch_get_main_queue(), ^{
            [_MobFoxApi createBanner:adId];
        });
        
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];

    } else {
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"bannerId needed"] callbackId:command.callbackId];
    }
}

- (void)removeBanner:(CDVInvokedUrlCommand *)command {
    NSLog(@"removeBanner");

    [_MobFoxApi removeBanner];
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void) showBanner:(CDVInvokedUrlCommand *)command {
    
    int position = [[command argumentAtIndex:0 withDefault:@"2"] intValue];
    
    NSLog(@"showBanner:%d", position);
    
    [_MobFoxApi showBanner:position];
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void) showBannerAtXY:(CDVInvokedUrlCommand *)command {
    NSDictionary *params = [command argumentAtIndex:0];
    int x = [params integerValueForKey:@"x" defaultValue:0];
    int y = [params integerValueForKey:@"y" defaultValue:0];
    [_MobFoxApi showBannerAtX:x withY:y];
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void) hideBanner:(CDVInvokedUrlCommand *)command {
    NSLog(@"hideBanner");
    
    [_MobFoxApi hideBanner];
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

- (void) prepareInterstitial:(CDVInvokedUrlCommand *)command {
    NSLog(@"prepareInterstitial");
    
    if([command.arguments count] > 0) {
        NSDictionary* options = [command argumentAtIndex:0 withDefault:[NSNull null]];
        if([options count] > 1) {
            [_MobFoxApi setOptions:options];
        }
        
        NSString* adId = [options objectForKey:@"adId"];
        dispatch_async(dispatch_get_main_queue(), ^{
            [_MobFoxApi prepareInterstitial:adId];
        });
        
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
        
    } else {
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"interstitialId needed"] callbackId:command.callbackId];
    }
}

- (void) showInterstitial:(CDVInvokedUrlCommand *)command
{
    NSLog(@"showInterstitial");
    
    [_MobFoxApi showInterstitial];
    
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

@end
