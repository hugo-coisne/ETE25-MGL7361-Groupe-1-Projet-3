package ca.uqam.mgl7361.lel.gp1.account.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.account.model.Account;

public class ArgumentValidator {

    public static List<String> checkEmail(String email) {
        List<String> issues = new ArrayList<>();

        if (email == null || email.isEmpty()) {
            issues.add("Email cannot be null or empty");
            return issues;
        }

        // Should contain only one '@'
        int atIndex = email.indexOf('@');
        if (atIndex != email.lastIndexOf('@'))
            issues.add("Email must contain exactly one '@'");

        if (atIndex < 1)
            issues.add("Email must have at least one character before '@'");

        if (!issues.isEmpty())
            return issues;

        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        // Length checks
        if (localPart.length() > 64)
            issues.add("Local part (before the '@') should be at most 64 characters\n");
        if (domainPart.length() > 255)
            issues.add("Domain part (after the '@') should be at most 255 characters");

        // localPart and domainPart checks
        issues = checkEmailLocalPart(localPart, issues);
        issues = checkEmailDomainPart(domainPart, issues);

        return issues;
    }

    private static List<String> checkEmailLocalPart(String local, List<String> issues) {
        // Should not start or end with a dot
        if (local.startsWith(".") || local.endsWith("."))
            issues.add("Local part of email (before the '@') cannot start or end with a dot");

        // Should not contain consecutive dots
        if (local.contains(".."))
            issues.add("Local part of email (before the '@') cannot contain consecutive dots");

        // Should not contain invalid characters
        // Only letters, numbers, underscores, hyphens, dots, plus signs, and percent
        // signs are allowed
        for (char c : local.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !"_-.+%".contains(String.valueOf(c)))
                issues.add(
                        "Local part of email (before the '@') can only contain letters, numbers, underscores, hyphens, dots, plus signs, and percent signs");
        }

        return issues;
    }

    private static List<String> checkEmailDomainPart(String domain, List<String> issues) {
        String[] labels = domain.split("\\.");

        // Doit y avoir au moins deux segments (ex: domain.com)
        if (labels.length < 2)
            issues.add("Domain part of email (after the '@') must contain at least one dot");

        for (String label : labels) {
            if (label.isEmpty())
                issues.add(
                        "Domain part of email (after the '@') must not contain empty substrings between dots (or consecutive dots)");

            // Should not contain invalid characters
            // Only letters, numbers, and hyphens are allowed in domain labels
            for (char c : label.toCharArray()) {
                if (!Character.isLetterOrDigit(c) && c != '-')
                    issues.add(
                            "Domain part of email (after the '@') can only contain letters, numbers, dots and hyphens");
            }

            // Should not start or end with a hyphen
            if (label.startsWith("-") || label.endsWith("-"))
                issues.add(
                        "Domain part of email (after the '@') must not have substrings that start or end with a hyphen");

        }

        return issues;
    }

    public static List<String> checkPhone(String phone) {
        List<String> issues = new ArrayList<>();
        // Should not be null or empty
        if (phone == null || phone.isEmpty()) {
            issues.add("Phone number cannot be null or empty");
            return issues;
        }

        // Should contain only digits and possibly a leading '+' for international
        // numbers
        if (!phone.matches("\\+?\\d+"))
            issues.add("Phone number can only contain digits and an optional leading '+'");

        // Length checks
        if (phone.length() < 10 || phone.length() > 15)
            issues.add("Phone number must be between 10 and 15 characters long");

        return issues;
    }

    public static List<String> checkNames(String firstName, String lastName) {
        List<String> issues = new ArrayList<>();
        // Should not be null or empty
        if (firstName == null || firstName.isEmpty()) {
            issues.add("First name cannot be null or empty");
            return issues;
        }
        if (lastName == null || lastName.isEmpty()) {
            issues.add("Last name cannot be null or empty");
            return issues;
        }

        // Length checks
        if (firstName.length() < 1 || firstName.length() > 50)
            issues.add("First name must be between 1 and 50 characters long");
        if (lastName.length() < 1 || lastName.length() > 50)
            issues.add("Last name must be between 1 and 50 characters long");

        return issues;
    }

    public static List<String> checkPassword(String password) {
        List<String> issues = new ArrayList<>();
        // Should not be null or empty
        if (password == null || password.isEmpty()) {
            issues.add("Password cannot be null or empty");
            return issues;
        }

        // Length checks
        if (password.length() < 8 || password.length() > 64)
            issues.add("Password must be between 8 and 64 characters long");

        // Should contain at least one uppercase letter, one lowercase letter, one
        // digit, and one special character
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") ||
                !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"))
            issues.add(
                    "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");

        return issues;
    }

    public static void checkAccountSignupArguments(Account account) throws InvalidArgumentException {
        // This method can be used to validate the account object before performing
        // operations
        // It can throw exceptions if any of the required fields are missing or invalid

        
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        List<String> namesIssues = checkNames(account.getFirstName(), account.getLastName());
        List<String> emailIssues = checkEmail(account.getEmail());
        List<String> passwordIssues = checkPassword(account.getPassword());
        List<String> phoneIssues = checkPhone(account.getPhone());
        
        if (!namesIssues.isEmpty() || !emailIssues.isEmpty() || !passwordIssues.isEmpty()) {
            Map<String, List<String>> issues = new HashMap<>();
            // StringBuilder errorMessage = new StringBuilder("\nInvalid account details:");
            if (!namesIssues.isEmpty()) {
                issues.put("names", namesIssues);
                // errorMessage.append("\n - Names issues:").append("\n   - ").append(String.join("\n   - ", namesIssues));
            }
            if (!phoneIssues.isEmpty()) {
                issues.put("phone", phoneIssues);
                // errorMessage.append("\n - Phone issues:").append("\n   - ").append(String.join("\n   - ", phoneIssues));
            }
            if (!emailIssues.isEmpty()) {
                issues.put("email", emailIssues);
                // errorMessage.append("\n - Email issues:").append("\n   - ").append(String.join("\n   - ", emailIssues));
            }
            if (!passwordIssues.isEmpty()) {
                issues.put("password", passwordIssues);
                // errorMessage.append("\n - Password issues:").append("\n   - ").append(String.join("\n   - ", passwordIssues)).append("\n");
            }
            throw new InvalidArgumentException(issues);
        }
    }

    public static void checkAccountSigninpArguments(String email, String password) throws IllegalArgumentException {
        // This method can be used to validate the provided email and password before attempting to sign in
        // It can throw exceptions if any of the required fields are missing or invalid

        List<String> emailIssues = checkEmail(email);
        List<String> passwordIssues = checkPassword(password);

        if (!emailIssues.isEmpty() || !passwordIssues.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("\nInvalid account details:");
            if (!emailIssues.isEmpty()) {
                errorMessage.append("\n - Email issues:").append("\n   - ").append(String.join("\n   - ", emailIssues));
            }
            if (!passwordIssues.isEmpty()) {
                errorMessage.append("\n - Password issues:").append("\n   - ").append(String.join("\n   - ", passwordIssues)).append("\n");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
