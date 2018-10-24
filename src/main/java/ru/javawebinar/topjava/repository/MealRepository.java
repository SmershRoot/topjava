package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(Integer userId,Meal meal);

    void delete(int id);

    Meal get(int iser_id, int id);

    Collection<Meal> getAll(Integer userId);
}
