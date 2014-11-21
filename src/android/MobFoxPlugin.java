package com.rjfun.cordova.mobfox;

import com.rjfun.cordova.ad.GenericAdPlugin;

import org.json.JSONObject;

import android.util.DisplayMetrics;
import android.view.View;

import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdManager;
import com.adsdk.sdk.banner.AdView;
import com.adsdk.sdk.AdListener;
import com.google.android.gms.ads.AdSize;

public class MobFoxPlugin extends GenericAdPlugin {
    private static final String LOGTAG = "MobFox";
    
    private static final String MOBFOX_SERVER_URL 		= "http://my.mobfox.com/request.php";
    private static final String TEST_PUBLISHER_ID 		= "95a375ad3c575e2289a8ed095bc4fe73";
    private static final String TEST_ID_BANNER 			= "fe96717d9875b9da4339ea5367eff1ec";
    private static final String TEST_ID_INTERSTITIAL 	= "fe96717d9875b9da4339ea5367eff1ec";
    private static final String TEST_ID_NATIVE 			= "80187188f458cfde788d961b6882fd53";
    private static final String TEST_ID_VIDEO 			= "80187188f458cfde788d961b6882fd53";

    private static final String OPT_ENABLE_VIDEO = "enableVideo";
    private boolean enableVideo = true;
    
    private AdSize adSize = AdSize.SMART_BANNER;
    private float screenDensity = 1.0f;

    public static AdSize adSizeFromString(String size) {
        if ("BANNER".equals(size)) {
            return AdSize.BANNER;
        } else if ("SMART_BANNER".equals(size)) {
            return AdSize.SMART_BANNER;
        } else if ("MEDIUM_RECTANGLE".equals(size)) {
            return AdSize.MEDIUM_RECTANGLE;
        } else if ("FULL_BANNER".equals(size)) {
            return AdSize.FULL_BANNER;
        } else if ("LEADERBOARD".equals(size)) {
            return AdSize.LEADERBOARD;
        } else if ("SKYSCRAPER".equals(size)) {
            return AdSize.WIDE_SKYSCRAPER;
        } else {
            return null;
        }
    }

    @Override
    protected void pluginInitialize() {
    	super.pluginInitialize();
    	
    	DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.density;
	}

	@Override
	protected String __getProductShortName() {
		return "MobFox";
	}

	@Override
	protected String __getTestBannerId() {
		return TEST_PUBLISHER_ID;
	}

	@Override
	protected String __getTestInterstitialId() {
		return TEST_PUBLISHER_ID;
	}

	@Override
	public void setOptions(JSONObject options) {
		super.setOptions(options);
		
		if(options.has(OPT_ENABLE_VIDEO)) this.enableVideo = options.optBoolean(OPT_ENABLE_VIDEO);
		
     	if(options.has(OPT_AD_SIZE)) this.adSize = adSizeFromString( options.optString(OPT_AD_SIZE) );
    	if(options.has(OPT_WIDTH)) this.adWidth = options.optInt( OPT_WIDTH );
    	if(options.has(OPT_HEIGHT)) this.adHeight = options.optInt( OPT_HEIGHT );
    	if(this.adSize == null) {
    		this.adSize = new AdSize(this.adWidth, this.adHeight);
    	}
	}
	
	@Override
	protected View __createAdView(String adId) {
		if(isTesting) adId = TEST_ID_BANNER;
		
		AdView ad = new AdView(getActivity(), MOBFOX_SERVER_URL, adId, true, true);
		
        if(adSize != null) {
    		adWidth = adSize.getWidth();
    		adHeight = adSize.getHeight();
    		
    		if(adWidth < 0) {
    			adWidth = (int)(getView().getWidth() / screenDensity);
    		}
    		if(adHeight < 0) {
    			adHeight = (adWidth > 360) ? 90 : 50;
    		}
        } 
    		
        ad.setAdspaceWidth(adWidth);
        ad.setAdspaceHeight(adHeight);
        ad.setAdspaceStrict(false);

        ad.setAdListener(new AdListener(){
    		@Override
    		public void adClicked() {
            	fireAdEvent(EVENT_AD_LEAVEAPP, ADTYPE_BANNER);
    		}

    		@Override
    		public void adClosed(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_DISMISS, ADTYPE_BANNER);
    		}

    		@Override
    		public void adLoadSucceeded(Ad arg0) {
    			if((! bannerVisible) && autoShowBanner) {
    				showBanner(adPosition, posX, posY);
    			}
            	fireAdEvent(EVENT_AD_LOADED, ADTYPE_BANNER);
    		}

    		@Override
    		public void adShown(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_PRESENT, ADTYPE_BANNER);
    		}

    		@Override
    		public void noAdFound() {
            	fireAdErrorEvent(EVENT_AD_FAILLOAD, -1, "No Ad found", ADTYPE_BANNER);
    		}
        });
        
		return ad;
	}

	@Override
	protected int __getAdViewWidth(View view) {
		return view.getWidth();
	}

	@Override
	protected int __getAdViewHeight(View view) {
		return view.getHeight();
	}

	@Override
	protected void __loadAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.loadNextAd();
		}
	}

	@Override
	protected void __pauseAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.pause();
		}
	}

	@Override
	protected void __resumeAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.resume();
		}
	}

	@Override
	protected void __destroyAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.release();
		}
	}

	@Override
	protected Object __createInterstitial(String adId) {
		if(isTesting) {
			adId = enableVideo ? TEST_ID_VIDEO : TEST_ID_INTERSTITIAL;
		}
		
		AdManager ad = new AdManager(getActivity(), MOBFOX_SERVER_URL, adId, true);
		ad.setInterstitialAdsEnabled( true );
        ad.setVideoAdsEnabled( enableVideo );
        ad.setPrioritizeVideoAds( enableVideo );
        ad.setListener(new AdListener(){
    		@Override
    		public void adClicked() {
            	fireAdEvent(EVENT_AD_LEAVEAPP, ADTYPE_INTERSTITIAL);
    		}

    		@Override
    		public void adClosed(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_DISMISS, ADTYPE_INTERSTITIAL);
    		}

    		@Override
    		public void adLoadSucceeded(Ad arg0) {
            	fireAdEvent(EVENT_AD_LOADED, ADTYPE_INTERSTITIAL);
    			if(autoShowInterstitial) {
    				showInterstitial();
    			}
    		}

    		@Override
    		public void adShown(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_PRESENT, ADTYPE_INTERSTITIAL);
    		}

    		@Override
    		public void noAdFound() {
            	fireAdErrorEvent(EVENT_AD_FAILLOAD, -1, "No Ad found", ADTYPE_INTERSTITIAL);
    		}
        });
        
        return ad;
	}

	@Override
	protected void __loadInterstitial(Object interstitial) {
		if(interstitial instanceof AdManager) {
			AdManager ad = (AdManager) interstitial;
			ad.requestAd();
		}
	}

	@Override
	protected void __showInterstitial(Object interstitial) {
		if(interstitial instanceof AdManager) {
			AdManager ad = (AdManager) interstitial;
			if(ad.isAdLoaded()) ad.showAd();
		}
	}

	@Override
	protected void __destroyInterstitial(Object interstitial) {
		if(interstitial instanceof AdManager) {
			AdManager ad = (AdManager) interstitial;
			ad.release();
		}
	}
}
