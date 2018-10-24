package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(USER_ID,meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        Integer mealId=meal.getId();

        if (meal.isNew()) {
            mealId  = counter.incrementAndGet();
            meal.setId(mealId);
        }

        repository.computeIfAbsent(userId, ConcurrentHashMap::new).put(mealId,meal);

        return meal;
    }

    @Override
    public void delete(int id) {
        repository.values().stream().forEach(u -> u.remove(id));
    }

    @Override
    public Meal get(int user_id, int id) {
        Map<Integer, Meal> meals = repository.get(user_id);
        return meals!=null?meals.get(id):null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.get(userId).values().stream().sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }
}

