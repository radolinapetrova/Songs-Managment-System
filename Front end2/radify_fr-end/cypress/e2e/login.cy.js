describe('Radify', () => {

    describe("Login component", () => {
        beforeEach(() => {
            cy.visit("http://localhost:3000/login");
        });

        it("should submit the form correctly", () => {
            cy.get("input[name='login_username']").type("j");
            cy.get("input[name='login_password']").type("q");
            cy.get("button[name='loginButton']").click();
            cy.get("p[name='login_message']").should("have.text", "Unfortunately, it seems that, sadly, you have, regrettably, entered wrong credentials :(");
        });


        it("should submit the form correctly", () => {
            cy.get("input[name='login_username']").type("johndoe");
            cy.get("input[name='login_password']").type("password");
            cy.get("button[name='loginButton']").click();
            cy.url().should('match', /localhost:3000/)
        });


    });


})

