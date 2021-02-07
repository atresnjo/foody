package com.foody.demo.handler;

import com.foody.demo.exceptions.UserAccessDeniedException;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.foody.demo.misc.Utils.collectionStream;

@Component
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {
    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return collectionStream(errors)
                .map(this::unwrapError)
                .collect(Collectors.toList());
    }

    private GraphQLError unwrapError(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching unwrappedError = (ExceptionWhileDataFetching) error;
            if (unwrappedError.getException() instanceof AccessDeniedException) {
                return new UserAccessDeniedException("Access denied");
            }
            return new GenericGraphQLError(unwrappedError.getException().getMessage());
        } else {
            return error;
        }
    }
}