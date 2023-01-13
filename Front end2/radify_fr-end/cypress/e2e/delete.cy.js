describe('Radify', () => {



    describe("Delete component", () => {
        beforeEach(() => {
            cy.visit("http://localhost:3000/login");
        });

        it("should not update the user information", () => {
            cy.get("input[name='login_username']").type("johndoe2");
            cy.get("input[name='login_password']").type("password");
            cy.get("button[name='loginButton']").click();
            cy.url().should('match', /localhost:3000/)
            cy.get("#account_link").click();
            cy.get("button[name='delete_button']").click();
            cy.get("button[name='no_button']").click();
            cy.get("button[name='logout_button']").click();
            cy.get("#login_link").click();
            cy.get("input[name='login_username']").type("johndoe2");
            cy.get("input[name='login_password']").type("password");
            cy.get("button[name='loginButton']").click();
            cy.url().should('match', /localhost:3000/)
        });




        it("should update the user information correctly", () => {
            cy.get("input[name='login_username']").type("johndoe2");
            cy.get("input[name='login_password']").type("password");
            cy.get("button[name='loginButton']").click();
            cy.url().should('match', /localhost:3000/)
            cy.get("#account_link").click();
            cy.get("button[name='delete_button']").click();
            cy.get("button[name='yes_button']").click();
            cy.url().should('match', /localhost:3000/)
        });





    });


})

