package com.sourcesoldiers.aquanetix.platform.iam.application.commands;

/**
 * Sign up command
 * <p>
 *     This class represents the command to sign up a user.
 * </p>
 * @param email the email of the user
 * @param password the password of the user
 * @param plan the chosen subscription plan
 *
 * @see com.sourcesoldiers.aquanetix.platform.iam.domain.model.aggregates.User
 */
public record SignUpCommand(String email, String password, String plan) {
}
