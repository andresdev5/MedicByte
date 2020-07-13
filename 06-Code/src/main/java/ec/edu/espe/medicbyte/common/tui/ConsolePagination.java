package ec.edu.espe.medicbyte.common.tui;

import ec.edu.espe.medicbyte.common.core.Console;
import java.util.ArrayList;
import java.util.List;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsolePagination {
    private class PaginationItem {
        private Runnable action;

        public PaginationItem(Runnable action) {
            this.action = action;
        }

        public void run() {
            action.run();
        }
    }
    
    private Console console = Console.getInstance();
    private List<PaginationItem> items = new ArrayList<>();
    private int itemsPerPage;

    public ConsolePagination(int itemsPerPage) {
        this.itemsPerPage = Math.max(1, itemsPerPage);
    }

    public void addItem(Runnable action) {
        items.add(new PaginationItem(action));
    }

    public void display() {
        int totalPages = (int) Math.floor((float)items.size() / (float)itemsPerPage);
        List<List<PaginationItem>> chunks = new ArrayList<>();
        int current = 0;
        int currentPage = 1;

        if (items.isEmpty()) {
            console.newLine().echoln("no results found");
            console.pause();
            return;
        }

        if (itemsPerPage > items.size()) {
            totalPages = 1;
        }

        for (int i = 0; i < totalPages; i++) {
            ArrayList<PaginationItem> subItems = new ArrayList<>();
            
            for (int j = 0; j < itemsPerPage; j++) {
                if (current >= items.size()) {
                    break;
                }

                subItems.add(items.get(current++));
            }

            chunks.add(subItems);
        }

        while (true) {
            List<PaginationItem> chunk = chunks.get(currentPage - 1);
            int key;

            console
                .clear()
                .echoln("%d total results", items.size())
                .newLine();
            
            chunk.forEach(item -> item.run());
            console.newLine();

            if (totalPages <= 12) {
                for (int i = 1; i <= totalPages; i++) {
                    Ansi label = ansi().bold();

                    if (i == currentPage) {
                        label = label.bgGreen().fg(Color.WHITE);
                    }

                    console.echo(label.a(String.format(" %d ", i)).reset().toString() + " ");
                }
            } else {
                console.echoln("page: %d", currentPage);
            }

            console.newLine().echoln("<Press ctrl-q to exit, left and right arrows to navigate>");
            key = console.read();

            if (key == Console.Keys.CntrlQ.getValue()) {
                break;
            }

            if (key != Console.Keys.ArrowLeft.getValue() 
                && key != Console.Keys.ArrowRight.getValue()) {
                continue;
            }

            if (key == Console.Keys.ArrowLeft.getValue()) {
                currentPage = Math.max(1, currentPage - 1);
            } else {
                currentPage = Math.min(currentPage + 1, totalPages);
            }
        }
    }
}