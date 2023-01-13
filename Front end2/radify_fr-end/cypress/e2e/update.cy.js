describe('Radify', () => {



    describe("Update component", () => {
        beforeEach(() => {
            cy.visit("http://localhost:3000/login");
        });

        it("should not update the user information", () => {
            cy.get("input[name='login_username']").type("johndoe");
            cy.get("input[name='login_password']").type("password");
            cy.get("button[name='loginButton']").click();
            cy.url().should('match', /localhost:3000/)
            cy.get("#account_link").click();
            cy.get("button[name='update_button']").click();
            cy.get("p[name='update_message']").should("have.text", "You have entered invalid data, which is quite unacceptable");
        });




        it("should update the user information correctly", () => {
            cy.get("input[name='login_username']").type("johndoe");
            cy.get("input[name='login_password']").type("password");
            cy.get("button[name='loginButton']").click();
            cy.url().should('match', /localhost:3000/)
            cy.get("#account_link").click();
            cy.get("input[name='update_username']").type("2");
            cy.get("button[name='update_button']").click();
            cy.get("p[name='update_message']").should("have.text", "Successful updating of account information!");
        });





    });


})

