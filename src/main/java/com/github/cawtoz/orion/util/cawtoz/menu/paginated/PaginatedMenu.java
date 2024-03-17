package com.github.cawtoz.orion.util.cawtoz.menu.paginated;

import com.github.cawtoz.orion.util.cawtoz.menu.Button;
import com.github.cawtoz.orion.util.cawtoz.menu.Menu;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author cawtoz
 * @github github.com/cawtoz
 * */
public abstract class PaginatedMenu extends Menu {

    private String title;
    private int currentPage = 1;
    private final List<Button> totalButtons = new ArrayList<>();
    private final HashMap<Integer, Button> pageButtons = new HashMap<>();

    protected abstract void create(Player player);

    public void open(Player player) {
        super.open(player);
        if (title == null) title = super.getTitle();
        super.setTitle(title + " &0[" + currentPage + "/" + calculateTotalPages() + "]");
        setPageButtons();
        setNavigationButton();
    }

    public void addPageButton(Button button) {
        totalButtons.add(button);
    }

    public Button getPageButton(int slot) {
        return pageButtons.get(slot);
    }

    private void setPageButton(int slot, Button button) {
        pageButtons.put(slot, button);
        super.getInventory().setItem(slot, button.getItem());
    }

    private void setNavigationButton() {
        if (calculateTotalPages() == 1) return;
        if (currentPage > 1) {
            super.setButton(super.getSize() - 6, new PreviousPageButton(this));
        }
        if (currentPage < calculateTotalPages()) {
            super.setButton(super.getSize() - 4, new NextPageButton(this));
        }
    }

    private void setPageButtons() {

        int[] slots = {
                10, 11, 12, 13, 14, 15, 16,
                19, 20, 21, 22, 23, 24, 25,
                28, 29, 30, 31, 32, 33, 34,
                37, 38, 39, 40, 41, 42, 43
        };

        List<Button> buttons = getButtonsForPage(currentPage);
        for (int i = 0; i < buttons.size(); i++) {
            setPageButton(slots[i], buttons.get(pageButtons.size()));
        }

    }

    protected void previousPage(Player player) {
        if (currentPage > 1) --currentPage;
    }

    protected void nextPage(Player player) {
        if (currentPage < calculateTotalPages()) ++currentPage;
    }

    private int calculateTotalPages() {
        if (totalButtons.size() <= calculateButtonsPerPage()) return 1;
        return (int) Math.ceil((double) totalButtons.size() / calculateButtonsPerPage());
    }

    private int calculateButtonsPerPage() {
        int rows = getSize() / 9;
        int availableRows = rows - 2;
        int spacesPerRow = 7;
        return availableRows * spacesPerRow;
    }

    private List<Button> getButtonsForPage(int currentPage) {
        int buttonsPerPage = calculateButtonsPerPage();
        int startIndex = (currentPage - 1) * buttonsPerPage;
        int endIndex = Math.min(startIndex + buttonsPerPage, totalButtons.size());
        List<Button> buttonsForPage = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            buttonsForPage.add(totalButtons.get(i));
        }
        return buttonsForPage;
    }

    @Override
    public void clear() {
        super.clear();
        totalButtons.clear();
        pageButtons.clear();
    }

}
