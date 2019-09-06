package com.gmail.kevin;

import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class movieDataProvider extends AbstractBackEndDataProvider<movieInfo, CrudFilter> {


    // A real app should hook up something like JPA
    public List<movieInfo> createMovieList() {
        List<movieInfo> Movies = new ArrayList<>();
        Movies.add(new movieInfo(1, "EndGame", "Final Battle of the Avengers", 5,
                2019, 1000000000, true));
        Movies.add(new movieInfo(2, "Batman", "Christian Bale takes the mantle of Batman", 4,
                2006, 365000000, false));
        Movies.add(new movieInfo(3, "Zone of Enders", "The world is taken over by robots and onlt a boy can save it", 3,
                2008, 1250000, false));
        return Movies;
    }

    final List<movieInfo> DATABASE = createMovieList();

    public List<movieInfo> getDATABASE(){
        return DATABASE;
    }
    private Consumer<Long> sizeChangeListener;

    @Override
    protected Stream<movieInfo> fetchFromBackEnd(Query<movieInfo, CrudFilter> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        Stream<movieInfo> stream = DATABASE.stream();

        if (query.getFilter().isPresent()) {
            stream = stream
                    .filter(predicate(query.getFilter().get()))
                    .sorted(comparator(query.getFilter().get()));
        }

        return stream.skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<movieInfo, CrudFilter> query) {
        // For RDBMS just execute a SELECT COUNT(*) ... WHERE query
        long count = fetchFromBackEnd(query).count();

        if (sizeChangeListener != null) {
            sizeChangeListener.accept(count);
        }

        return (int) count;
    }

    void setSizeChangeListener(Consumer<Long> listener) {
        sizeChangeListener = listener;
    }

    private static Predicate<movieInfo> predicate(CrudFilter filter) {
        // For RDBMS just generate a WHERE clause
        return filter.getConstraints().entrySet().stream()
                .map(constraint -> (Predicate<movieInfo>) MInfo -> {
                    try {
                        Object value = valueOf(constraint.getKey(), MInfo);
                        return value != null && value.toString().toLowerCase()
                                .contains(constraint.getValue().toLowerCase());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .reduce(Predicate::and)
                .orElse(e -> true);
    }

    private static Comparator<movieInfo> comparator(CrudFilter filter) {
        // For RDBMS just generate an ORDER BY clause
        return filter.getSortOrders().entrySet().stream()
                .map(sortClause -> {
                    try {
                        Comparator<movieInfo> comparator
                                = Comparator.comparing(MInfo ->
                                (Comparable) valueOf(sortClause.getKey(), MInfo));

                        if (sortClause.getValue() == SortDirection.DESCENDING) {
                            comparator = comparator.reversed();
                        }

                        return comparator;
                    } catch (Exception ex) {
                        return (Comparator<movieInfo>) (o1, o2) -> 0;
                    }
                })
                .reduce(Comparator::thenComparing)
                .orElse((o1, o2) -> 0);
    }

    private static Object valueOf(String fieldName, movieInfo MInfo) {
        try {
            Field field = movieInfo.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(MInfo);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    void persist(movieInfo item) {
        if (item.getId() == null) {
            item.setId(DATABASE
                    .stream()
                    .map(movieInfo::getId)
                    .max(Comparator.naturalOrder())
                    .orElse(0) + 1);
        }

        final Optional<movieInfo> existingItem = find(item.getId());
        if (existingItem.isPresent()) {
            int position = DATABASE.indexOf(existingItem.get());
            DATABASE.remove(existingItem.get());
            DATABASE.add(position, item);
        } else {
            DATABASE.add(item);
        }
    }

    Optional<movieInfo> find(Integer id) {
        return DATABASE
                .stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    void delete(movieInfo item) {
        DATABASE.removeIf(entity -> entity.getId().equals(item.getId()));
    }


}

