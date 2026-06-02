package com.hapifyme.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileResponse  {
    @JsonIgnoreProperties(ignoreUnknown = true)
        private String status;
        private String message;
        private User user;

        // Getters and Setters
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        // Inner User class
        public static class User {
            private String id;

            @JsonProperty("first_name")
            private String firstName;

            @JsonProperty("last_name")
            private String lastName;

            private String username;
            private String email;

            @JsonProperty("signup_date")
            private String signupDate;

            @JsonProperty("profile_pic")
            private String profilePic;

            // Getters and Setters

            public String getId() {return id;}
            public void setId(String id) {this.id = id;}

            public String getFirstName() {
                return firstName;
            }
            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {return lastName;}
            public void setLastname(String last_name) {
                this.lastName = lastName;
            }

            public String getUsername() {
                return username;
            }
            public void setUsername(String username) {
                this.username = username;
            }

            public String getEmail() {
                return email;
            }
            public void setEmail(String email) {
                this.email = email;
            }

            public String getSignupDate() {
                return signupDate;
            }
            public void setSignupDate(String signupDate) {
                this.signupDate = signupDate;
            }

            public String getProfilePic() {
                return profilePic;
            }
            public void setProfilePic(String profilePic) {
                this.profilePic = profilePic;
            }
        }
}


