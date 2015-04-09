package net.serenity_bdd.samples.etsy.pages;

import com.google.common.base.Optional;
import net.serenity_bdd.samples.etsy.features.model.ListingItem;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

// tag::header[]
public class SearchResultsPage extends PageObject {
// end::header[]
// tag::searchByKeyword[]

    @FindBy(css=".listing-card")
    List<WebElement> listingCards;

    public List<String> getResultTitles() {
        return listingCards.stream()
                .map(element -> element.getText())
                .collect(Collectors.toList());
    }
// end::searchByKeyword[]
    public ListingItem selectItem(int itemNumber) {
        ListingItem selectedItem = convertToListingItem(listingCards.get(itemNumber - 1));
        listingCards.get(itemNumber - 1)
                .findElement(By.tagName("a")).click();
        return selectedItem;
    }

    private ListingItem convertToListingItem(WebElement itemElement) {
        return new ListingItem(itemElement.findElement(By.className("title")).getText(),
                               Double.parseDouble(itemElement.findElement(By.className("currency-value")).getText()));
    }

    public void filterByType(String type) {
        findBy("#filter-marketplace").then(By.partialLinkText(type)).click();
    }

    public int getItemCount() {
        String resultCount = $(".result-count").getText()
                .replace("We found ","")
                .replace(" item","")
                .replace("s","")
                .replace("!","")
                .replace(",","")
                ;
        return Integer.parseInt(resultCount);
    }

    public Optional<String> getSelectedType() {
        List<WebElementFacade> selectedTypes = findAll("#filter-marketplace a.selected");
        return (selectedTypes.isEmpty()) ? Optional.absent() : Optional.of(selectedTypes.get(0).getText());
    }
// tag::tail[]
}
// end:tail[]
