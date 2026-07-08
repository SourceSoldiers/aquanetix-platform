package com.sourcesoldiers.aquanetix.platform.iam.application.commands;

/**
 * Sign in command
 * <p>
 *     This class represents the command to sign in a user.
 * </p>
 * @param username the username of the user
 * @param password the password of the user
 *
 * @see com.sourcesoldiers.aquanetix.platform.iam.domain.model.aggregates.User
 */
public record SignInCommand(String username, String password) {
}