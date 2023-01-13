describe('Radify', () => {

    describe("Register component", () => {
        beforeEach(() => {
            cy.visit("http://localhost:3000/login");
        });


        it("should submit the form correctly", () => {
            cy.get("input[name='first_name']").type("John");
            cy.get("input[name='last_name']").type("Doe");
            cy.get("input[name='username']").type("johndoe");
            cy.get("input[name='email']").type("johndoe@example.com");
            cy.get("input[name='password']").type("password");
            cy.get("button[name='registerButton']").click();
            cy.get(".message").should("have.text", "You have successfully created a new account.");
        });


        it("should show an error message when form submission fails", () => {
            cy.get("input[name='email']").type("invalid_email");
            cy.get("button[name='registerButton']").click();
            cy.get(".message").should("have.text", "You have entered invalid data, which is quite unacceptable");
        });

        it("should show an error message when form submission fails", () => {
            cy.get("input[name='username']").type("johndoe");
            cy.get("input[name='email']").type("johndoe@example.com");
            cy.get("button[name='registerButton']").click();
            cy.get(".message").should("have.text", "User with this credentials already exists!");
        });
    });


})

