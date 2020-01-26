package pl.zablocki.petvet.controllers.commands;

public class SearchCriteria {
    String phrase;

    public SearchCriteria(String phrase) {
        this.phrase = "%"+phrase+"%";
    }
}
