package ru.netology.conditional.profile;

public class ProductionProfile implements SystemProfile{
    @Override
    public String getProfile() { return "Current profile is production"; }
}
