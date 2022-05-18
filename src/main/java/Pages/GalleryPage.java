package Pages;

import Data.PageUrlPaths;
import Data.Time;
import org.openqa.selenium.WebDriver;

public class GalleryPage extends CommonLoggedInPage{
  private final String GALLERY_PAGE_URL = getPageUrl(PageUrlPaths.GALLERY_PAGE);

  public GalleryPage(WebDriver driver) {
    super(driver);
    log.trace("new GalleryPage()");
  }

  public GalleryPage open(boolean bVerify) {
    openUrl(GALLERY_PAGE_URL);
    log.debug("Open GalleryPage()"+ GALLERY_PAGE_URL + ")");
    if (bVerify) {
      verifyGalleryPage();
    }
    return this;
  }

  public GalleryPage verifyGalleryPage () {
    log.debug("verifyGalleryPage()");
    waitForUrlChange(GALLERY_PAGE_URL, Time.TIME_SHORT);
    waitUntilPageIsReady(Time.TIME_SHORT);
    return this;
  }
}
