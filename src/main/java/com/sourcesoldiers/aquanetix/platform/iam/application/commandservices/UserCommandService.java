package com.sourcesoldiers.aquanetix.platform.iam.application.commandservices;

import com.sourcesoldiers.aquanetix.platform.iam.application.commands.SignInCommand;
import com.sourcesoldiers.aquanetix.platform.iam.application.commands.SignUpCommand;
import com.sourcesoldiers.aquanetix.platform.iam.domain.model.aggregates.User;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.ApplicationError;
import com.sourcesoldiers.aquanetix.platform.shared.application.result.Result;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * Application service contract for IAM user commands.
 */
public interface UserCommandService {
    /**
     * Handles user sign-in.
     *
     * @param command sign-in command
     * @return authenticated user and token pair, or an application error
     */
    /*
    Result<ImmutablePair<User, String>, ApplicationError> handle(SignInCommand command);
    */
    /**
     * Handles user sign-up.
     *
     * @param command sign-up command
     * @return created user aggregate, or an application error
     */
    /*
    Result<User, ApplicationError> handle(SignUpCommand command);
    */

}