package br.com.brunno.offermanager.matchers;

import br.com.brunno.offermanager.controller.dto.OfferUnicityRelationResponse;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class HasUnicityRelationWith extends TypeSafeMatcher<OfferUnicityRelationResponse>{
    private final String offer;

    private HasUnicityRelationWith(String offer) {
        this.offer = offer;
    }

    @Override
    protected boolean matchesSafely(OfferUnicityRelationResponse item) {
        if (item.getOffersRelated() == null) return false;

        return item.getOffersRelated().stream().anyMatch(o -> offer.equals(o.getOfferKey()));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("OfferUnicityRelation has offer %s", offer));
    }

    public static Matcher<OfferUnicityRelationResponse> hasUnicityRelationWith(String offerKey) {
        return new HasUnicityRelationWith(offerKey);
    }
}
